package game.resets;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 * TODO: you may modify (add or remove) methods in this class if you think they are not necessary.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 * A3: Think about how will you improve this implementation in the future assessment.
 * What could be the drawbacks of this implementation?
 */
public class ResetManager {
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     * Stores Resettables and a Boolean indicating true if their reset is in progress.
     */
    private final List<Resettable> resettableList;

    /**
     * A flag indicating whether a reset is available or the single reset has been executed.
     * This can be changed to an integer to allow a finite number of reset greater than one.
     */
    private boolean resetAvailable;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if(instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private ResetManager(){
        this.resettableList = new ArrayList<>();
        this.resetAvailable = true;     // Reset initially available
    }

    /**
     * Checks if a global reset is available.
     * @return true iff a reset is available.
     */
    public boolean isResetAvailable() {
        return this.resetAvailable;
    }

    /**
     * Reset the game by traversing through all the list
     * By doing this way, it will avoid using `instanceof` all over the place.
     */
    public void run() {
        // Execute the reset on all Resettable objects
        if (this.resetAvailable) {
            for (Resettable resettableObject : this.resettableList) {
                resettableObject.resetInstance();
//                this.cleanUp(resettableObject);
            }
        }

        this.resetAvailable = false;    // Cannot perform another reset
    }

    /**
     * Add the Resettable instance to the list
     * FIXME: it does nothing, you need to implement it :)
     */
    public void appendResetInstance(Resettable reset){
        this.resettableList.add(reset);
    }


    /**
     * Remove a Resettable instance from the list
     * @param resettable resettable object
     * FIXME: it does nothing, you need to implement it :)
     */
    public void cleanUp(Resettable resettable){
        this.resettableList.remove(resettable); // Remove the resettable to prevent further resets
    }
}
