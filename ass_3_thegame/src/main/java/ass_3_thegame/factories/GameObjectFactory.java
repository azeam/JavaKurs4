package ass_3_thegame.factories;

import java.util.ArrayList;
import java.util.Random;

import ass_3_thegame.Container;
import ass_3_thegame.GameObject;
import ass_3_thegame.Key;
import ass_3_thegame.Room;


// TODO: randomize only one master key somewhere, set global int?
// TODO: only make non pickable in rooms
// TODO: make random object

public class GameObjectFactory {
    public GameObject createGameObject(int number, boolean onlyPickable, Room room) {
        if (onlyPickable) {
            return new Key(room);
        }
        else {
            int[] options = {1, 2};
            Random random = new Random();
            int select = random.nextInt(options.length); 
            int randObject = options[select];
            switch (randObject) {
                case 1: return new Key(room);
                case 2: return new Container(room);
            }            
        }
        return null;
    }

	public ArrayList<GameObject> createGroup(int number, boolean onlyPickable, Room room) {
        ArrayList<GameObject> group = new ArrayList<GameObject>();
        for (int i=0; i<number; i++) {
            group.add(createGameObject(number, onlyPickable, room));
        }
        return group;
	}
}
