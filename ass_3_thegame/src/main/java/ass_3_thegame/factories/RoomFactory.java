package ass_3_thegame.factories;

import java.util.ArrayList;

import ass_3_thegame.Inventory;
import ass_3_thegame.Room;

public class RoomFactory {
    public Room createRoom(int id, Inventory inventory) {
        return new Room(id, inventory);
    }

	public ArrayList<Room> createGroup(int number, Inventory inventory) {
        ArrayList<Room> group = new ArrayList<Room>();
        for (int i=0; i<number; i++) {
            group.add(createRoom(i, inventory));
        }
        return group;
	}
}
