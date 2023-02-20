package game.actors.friendlies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.actions.*;
import game.behaviours.SpeakBehaviour;
import game.items.magical.PowerStar;
import game.items.magical.SuperMushroom;
import game.items.Wrench;
import game.monologues.Monologue;
import game.monologues.Speakable;

import java.util.ArrayList;
import java.util.List;

public class Toad extends Friendly {

    /**
     * Default constructor for Toad. Toad's details are always the same.
     */
    public Toad() {
        super("Toad", 'O', 100);
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // NOTE: It is assumed that Toad will speak every second turn in addition to the player speaking with Toad.
        Action friendlyAction = super.playTurn(actions, lastAction, map, display);
        if (null != friendlyAction)
            return friendlyAction;
        return new DoNothingAction();
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList list = new ActionList();

        // Trade actions
        list.add(new TradeAction(new Wrench(),200));
        list.add(new TradeAction(new SuperMushroom(),400));
        list.add(new TradeAction(new PowerStar(),600));

        // Monologue actions
        // Don't use behaviour as actor executing is not the actor speaking
        list.add(new SpeakAction(this, this, this.getMonologues(this)));

        return list;
    }

    @Override
    public List<Monologue> getMonologues(Actor actor) {
        // Get allowable monologues based on player inventory and status
        List<Monologue> monologues = new ArrayList<>();

        // Check if player holds wrench
        if (actor.hasCapability(Status.DESTROY_SHELL)) {
            monologues.add(new Monologue("You might need a wrench to smash Koopa's hard shells."));
        }

        // Check if power star effect is active
        if (!actor.hasCapability(Status.ItemEffect.POWERSTAR)) {
            monologues.add(new Monologue("You better get back to finding the Power Stars."));
        }

        // Unconditional sentences
        monologues.add(new Monologue("The Princess is depending on you! You are our only hope."));
        monologues.add(new Monologue("Being imprisoned in these walls can drive a fungus crazy :("));

        return monologues;
    }
}
