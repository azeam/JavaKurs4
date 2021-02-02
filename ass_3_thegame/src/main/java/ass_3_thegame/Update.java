package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import ass_3_thegame.factories.NpcFactory;
import ass_3_thegame.factories.RoomFactory;

public class Update implements Runnable {

    // Ska implementera Runnable och starta en tråd som Regelbundet updaterar Guit
    // utifrån vad som händer i spelet.
    Gui gui;
    Painter painter = new Painter();

    public Update(Gui gui) {
        this.gui = gui;
	}

	@Override
    public void run() {
        Names names = new Names();
        NpcFactory npcFactory = new NpcFactory();
        RoomFactory roomFactory = new RoomFactory();

        List<String> namesList = names.getRandomNames(Constants.NUM_NPCS);
        ArrayList<Npc> personGroup = npcFactory.createGroup("Person", Constants.NUM_NPCS, namesList);
        ArrayList<Room> roomGroup = roomFactory.createGroup(Constants.NUM_ROOMS);

        // TODO: get room inventory and update gui
        gui.setUpWalls(roomGroup);
        gui.setUpPerson(personGroup);
        updateGui(personGroup);
    }

    private void updateGui(ArrayList<Npc> personGroup) {
        Direction curDir;
        int newX, newY;

        while (true) {
            try {
                for (Npc person: personGroup) {
                    curDir = person.getDirection();
                    newX = person.getPosX() + curDir.getX();
                    newY = person.getPosY() + curDir.getY();
                    if (painter.collision(person, newX, newY)) {
                        person.setDirection(Direction.getOpposite(person.getDirection())); 
                        curDir = person.getDirection();
                        newX = person.getPosX() + curDir.getX();
                        newY = person.getPosY() + curDir.getY();    
                    }
                    
                    changePos(person, newX, newY);                   
                }
                gui.setShowObjects(personGroup);
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        }
    }

    private void changePos(Npc person, int newX, int newY) {
        person.setPosX(newX);
        person.setPosY(newY);   
        person.setCurRoom();

        int room = person.getCurRoom();
        System.out.println(person.npcName() + " is in room: " + room); 
    }
    
}
