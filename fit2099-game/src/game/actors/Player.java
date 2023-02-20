package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Status;
import game.actions.ResetGameAction;
import game.items.Coin;
import game.items.Consumable;
import game.resets.Resettable;
import game.resets.ResetManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

	/**
	 * The menu of player actions displayed to the console.
	 */
	private final Menu menu = new Menu();

	/**
	 * The player's wallet containing the values of collected coins.
	 */
	private final Wallet wallet;

	/**
	 * Signals whether to perform a reset.
	 */
	private boolean resetFlag;

	/**
	 * A collection of all effects that should be removed from the player capabilities and items in the players
	 * inventory on a reset.
	 */
	private final List<Enum<?>> resetEffects;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hit points
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.registerInstance();    // Register player for global reset
		this.resetFlag = false;
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		wallet = new Wallet(100);

		// Effects that should be removed from the player on a reset
		this.resetEffects = new ArrayList<>();
		this.resetEffects.add(Status.ItemEffect.POWERSTAR);
		this.resetEffects.add(Status.ItemEffect.SUPERMUSH);
		this.resetEffects.add(Status.POWER_UP);
		this.resetEffects.add(Status.TALL);
	}

	/**
	 * Select and return an action to perform on the current turn.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Player reset
		if (this.resetFlag) {
			this.resetEffects();    // Reset actor and item capabilities
			this.heal(this.getMaxHp()); // Heal player
			this.resetFlag = false;
			return menu.showMenu(this, actions, display);
		}

		System.out.println(displayString()); // Testing - you can use display.println()
		printCapabilities();				// Testing
		System.out.println(getInventory());

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// Handle single-use reset
		if (ResetManager.getInstance().isResetAvailable()) {
			actions.add(new ResetGameAction());
		}

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	@Override
	public void addItemToInventory(Item item) { // Impossible to implement coin pickup functionality elsewhere
		if (item.getClass()== Coin.class ){
			int coinValue = ((Coin) item).getValue();
			wallet.deposite(coinValue);
		}else{
			super.addItemToInventory(item);
		}
	}

	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

	@Override
	public void hurt(int points) {
		if(this.hasCapability(Status.ItemEffect.POWERSTAR)){
			return;	// No damage
		}
		super.hurt(points);
		// get mushroom responsible for SUPERMUSH effect, if exists

		if (this.hasCapability(Status.ItemEffect.SUPERMUSH) ){
			Consumable mushroom = (Consumable) selectInventoryItemByEffect(Status.ItemEffect.SUPERMUSH);
			mushroom.resetEffect(this);
		}
	}

	/**
	 * Retrieves the player's wallet balance.
	 * @return The amount of money the player currently has.
	 */
	public int getWalletBalance(){
		return wallet.getBalance();
	}

	/**
	 * Reduces the player's wallet balance.
	 * @param deduction The amount of money to reduce the balance by.
	 */
	public void deductWalletBalance(int deduction){
		wallet.deduct(deduction);
	}

	/**
	 * Increases the player's wallet balance.
	 * @param addValue The amount of money to add to the balance.
	 */
	public void addWalletBalance(int addValue){
		wallet.deposite(addValue);
	}

	/**
	 * print item effects
	 */
	public void printCapabilities() {
		ArrayList<Enum<?>> caps = new ArrayList<>(capabilitiesList());
		for (Item item:getInventory()){
			caps.addAll(item.capabilitiesList());
		}
		System.out.println(caps);
	}

	/**
	 * Each turn print information about player status and items
	 * @return string containing wallet balance
	 */
	private String displayString(){
		String balance = String.valueOf(wallet.getBalance());
		String hp = printHp();
		return "HP : "+hp+" Cash: $"+balance;
	}

	public Item selectInventoryItemByEffect(Status.ItemEffect effect){
		if(hasCapability(effect)){
			List<Item> inventory = getInventory();
			Item conItem = inventory.get(0);
			for (Item item: getInventory()){
				if (item.hasCapability(effect)){
					conItem = item;
					break;
				}
			}
			return conItem;
		}
		return null;
	}

	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		if (this.hasCapability(Status.POWER_UP)) {
			return new IntrinsicWeapon(20,"punches");
		}
		return super.getIntrinsicWeapon();
	}

	/**
	 * Resets all the player and item effects listed in this.resetEffects.
	 */
	private void resetEffects() {
		for (Enum<?> effect : this.resetEffects) {
			for (Item item : this.getInventory()) {
				if (item.hasCapability(effect))
					item.removeCapability(effect);
			}
			if (this.hasCapability(effect))
				this.removeCapability(effect);
		}
	}

	@Override
	public void resetInstance() {
		this.resetFlag = true;
	}
}
