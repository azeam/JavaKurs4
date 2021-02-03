package ass_3_thegame.factories;

import java.util.ArrayList;

import ass_3_thegame.GameObject;


// TODO: randomize only one master key somewhere, set global int?
// TODO: only make non pickable in rooms
// TODO: make random object

public class GameObjectFactory {
    public GameObject createGameObject(int number, boolean onlyPickable) {
        return new GameObject(onlyPickable);
    }

	public ArrayList<GameObject> createGroup(int number, boolean onlyPickable) {
        ArrayList<GameObject> group = new ArrayList<GameObject>();
        for (int i=0; i<number; i++) {
            group.add(createGameObject(number, onlyPickable));
        }
        return group;
	}
}
