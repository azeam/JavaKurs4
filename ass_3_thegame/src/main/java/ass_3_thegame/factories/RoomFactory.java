package ass_3_thegame.factories;

import java.util.ArrayList;

import ass_3_thegame.Room;

public class RoomFactory {
    public Room createRoom(int id) {
        return new Room(id);
    }

	public ArrayList<Room> createGroup(int number) {
        ArrayList<Room> group = new ArrayList<Room>();
        for (int i=0; i < number; i++) {
            group.add(createRoom(i));
        }
        return group;
	}
}
