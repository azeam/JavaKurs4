package ass_3_thegame.factories;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ass_3_thegame.Constants;
import ass_3_thegame.Container;
import ass_3_thegame.GameObject;
import ass_3_thegame.Key;
import ass_3_thegame.Room;


// TODO: randomize only one master key somewhere, set global int?
// TODO: only make non pickable in rooms

// one master key in a chest -> opens door
// other chests are empty
// for each chest make a key

public class GameObjectFactory {
    public GameObject createGameObject(boolean onlyPickable, Room room, boolean isMaster) {
        int id = ThreadLocalRandom.current().nextInt(0, (Constants.NUM_ROOMS * Constants.INV_SIZE_ROOM / 2) + 1);
        
        if (onlyPickable) {
            return new Key(room, id, isMaster);
        }
        else {
            int[] options = {1, 2};
            Random random = new Random();
            int select = random.nextInt(options.length); 
            int randObject = options[select];
            switch (randObject) {
                case 1: return new Key(room, id, isMaster);
                case 2: return new Container(room, id);
            }            
        }
        return null;
    }

	public ArrayList<GameObject> createGroup(int number, boolean onlyPickable, Room room) {
        ArrayList<GameObject> group = new ArrayList<GameObject>();
        for (int i=0; i<number; i++) {
            group.add(createGameObject(onlyPickable, room, false));
        }
        return group;
	}
}
