package game.actions;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.items.*;
import edu.monash.fit2099.engine.positions.*;
import game.actors.*;

public class TradeAction extends Action {
    private Item tradeItem;
    private int tradeValue;

    public TradeAction(Item tradeItem, int value) {
        this.tradeItem = tradeItem;
        this.tradeValue = value;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (  ((Player) actor).getWalletBalance() < tradeValue){
            return actor+" too poor to buy "+tradeItem+" for $"+tradeValue+" :(";
        }

        ((Player) actor).deductWalletBalance(tradeValue);
        actor.addItemToInventory(tradeItem);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor+" buys "+tradeItem+" for $"+tradeValue;
    }
}