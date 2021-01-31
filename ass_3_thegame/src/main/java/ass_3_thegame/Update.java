package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import ass_3_thegame.factories.NpcFactory;
import ass_3_thegame.factories.RoomFactory;

public class Update implements Runnable {

    // Ska implementera Runnable och starta en tråd som Regelbundet updaterar Guit
    // utifrån vad som händer i spelet.
    Gui gui;
    int numNPCs = 10; // API max is 10
    int numRooms = 4;

    public Update(Gui gui) {
        this.gui = gui;
	}

	@Override
    public void run() {
        Names names = new Names();
        NpcFactory npcFactory = new NpcFactory();
        RoomFactory roomFactory = new RoomFactory();

        List<String> namesList = names.getRandomNames(numNPCs);
        ArrayList<Npc> personGroup = npcFactory.createGroup("Person", numNPCs, namesList);
        ArrayList<Room> roomGroup = roomFactory.createGroup(numRooms);

        // TODO: get room inventory and update gui

        updateGui(personGroup, roomGroup);
    }

    private void updateGui(ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        Direction randomDir;
        int newX, newY, room;

        while (true) {
            try {
                for (Npc person: personGroup) {
                    randomDir = person.getDirection();
                    newX = person.getPosX() + randomDir.getX();
                    newY = person.getPosY() + randomDir.getY();
                    room = person.getCurRoom();
                    changePos(person, newX, newY, room);
                }
                gui.setShowPersons(personGroup, roomGroup);
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        }
    }

    private void changePos(Npc person, int newX, int newY, int room) {
        if (newX <= Constants.MARGIN || newY <= Constants.MARGIN || newX >= Constants.ALL_ROOMS_WIDTH + Constants.MARGIN || newY >= Constants.ROOM_HEIGHT + Constants.MARGIN) { 
            person.setDirection(Direction.getRandom());
        }
        else if (room == 1 && newX > Constants.ROOM_WIDTH) {
            setRoomBottomDoor(person, newY, room + 1);
        }
        else if (room == 2 && newX > Constants.ROOM_WIDTH * 2) {
            setRoomTopDoor(person, newY, room + 1);
        }
        else if (room == 2 && newX < Constants.ROOM_WIDTH) {
            setRoomBottomDoor(person, newY, room - 1);
        }
        else if (room == 3 && newX > Constants.ROOM_WIDTH * 3) {
            setRoomBottomDoor(person, newY, room + 1);
        }
        else if (room == 3 && newX < Constants.ROOM_WIDTH * 2) {
            setRoomTopDoor(person, newY, room - 1);
        }
        else if (room == 4 && newX < Constants.ROOM_WIDTH * 3) {
            setRoomBottomDoor(person, newY, room - 1);
        }
        else {
            person.setPosX(newX);
            person.setPosY(newY);    
        //    System.out.println(person.npcName() + " is in room " + person.getCurRoom());
        }
    }

    private void setRoomBottomDoor(Npc person, int newY, int newRoom) {
        if (newY > Constants.WALL_SIZE + Constants.MARGIN) {
            person.setCurRoom(newRoom);
        }
        else {
            person.setDirection(Direction.getRandom());
        }
    }

    private void setRoomTopDoor(Npc person, int newY, int newRoom) {
        if (newY < (Constants.ROOM_HEIGHT - Constants.WALL_SIZE + Constants.MARGIN)) {
            person.setCurRoom(newRoom);
        }
        else {
            person.setDirection(Direction.getRandom());
        }
    }

    
}
