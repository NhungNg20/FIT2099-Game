package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.grounds.Dirt;
import game.items.FireFlower;
import game.grounds.highgrounds.HighGround;
import game.resets.ResetManager;
//import game.resets.ResetResources;
import game.resets.Resettable;

/**
 * The abstract Tree class represents a tree in any stage of its lifecycle.
 */
public abstract class Tree extends HighGround implements Resettable {

    /**
     * Signals whether a reset should be implemented on the next tick.
     */
    private boolean resetFlag;

    /**
     * The age of a tree to allow growth
     */
    protected int age;

    /**
     * Constructor.
     * This is called for trees drawn on the initial game-map for which the location isn't
     * initially available.
     * @param name The tree's name.
     * @param displayChar The tree's display character on the GameMap.
     */
    public Tree(String name, char displayChar) {
        super(name, displayChar);
        this.age = 0;
        this.registerInstance();    // Register all Tree objects with ResetManager
        this.resetFlag = false;
    }

    /**
     * Constructor.
     * @param name The tree's name.
     * @param displayChar The tree's display character on the GameMap.
     * @param location The tree's location.
     */
    public Tree(String name, char displayChar, Location location) {
        super(name, displayChar);
        this.registerInstance();    // Register all Tree objects with ResetManager
        this.resetFlag = false;
    }

    /**
     * Overriding from Ground superclass
     * @param treeLocation the location of tree
     */
    @Override
    public void tick(Location treeLocation) {
        if (this.resetFlag) {
            // Trees have a 50% probability to be converted back to dirt during a reset
            if (0 < Utils.randInt(2)) {
                treeLocation.setGround(new Dirt());
            }
            this.resetFlag = false;
        }
        super.tick(treeLocation);
    }

    @Override
    public void resetInstance() {
        this.resetFlag = true;
    }

    public void grow(Location location){
        if (Utils.randInt(100) <= 50) {
            location.addItem(new FireFlower());
        }
    }
}


