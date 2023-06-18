package game.actions.attacks;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Utils;
import game.actions.attacks.AttackAction;

/**
 * A class representing a basic attack action for an actor
 */
public class GenAttackAction extends AttackAction {

    /**
     * Constructor.
     *
     * @param target The actor to attack
     * @param direction The direction of the attack
     */
    public GenAttackAction(Actor target, String direction) {
        super(target, direction);
    }

    /**
     * To run an generalised attack on an actor
     * @param attacker The actor attacking
     * @param map The map the actor is on.
     * @return the outcome of the attack as string
     */
    @Override
    public String execute(Actor attacker, GameMap map) {
        int damage;
        String suffix;
        Weapon weapon = attacker.getWeapon();

        if (invincibleActor(attacker)) {
            suffix = " but target is invincible!";
        } else if (Utils.randInt(100) > weapon.chanceToHit()){
            suffix = " but misses";
        } else {
            damage = weapon.damage();
            target.hurt(damage);
            suffix = " for " + damage + " damage";
        }
        if (!target.isConscious() | invincibleActor(attacker)) {
            super.dropItems(target, map);
            map.removeActor(target);
            suffix = " TO DEATH!";
        }
        return attacker + " " + weapon.verb() + " " + target + suffix;
    }
}
