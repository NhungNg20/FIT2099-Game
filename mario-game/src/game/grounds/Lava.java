package game.grounds;

import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.positions.*;
import game.*;

public class Lava  extends Ground {
    public Lava() {
        super('L');
        this.addCapability(Status.FERTILE);
    }

    @Override
    public void tick(Location myLocation){
        if(myLocation.containsAnActor()){
            Actor carelessActor = myLocation.getActor();
            carelessActor.hurt(15);
        }
    }
}