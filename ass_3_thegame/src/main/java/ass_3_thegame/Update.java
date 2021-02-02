package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import ass_3_thegame.factories.NpcFactory;
import ass_3_thegame.factories.RoomFactory;
import javafx.animation.AnimationTimer;

public class Update implements Runnable {

    // Ska implementera Runnable och starta en tråd som Regelbundet updaterar Guit
    // utifrån vad som händer i spelet.
    Gui gui;
    Painter painter = new Painter();
    static boolean paused = false;

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

        gui.setUpWalls(roomGroup);
        gui.setUpPerson(personGroup);
        updateGui(personGroup, roomGroup);
    }

    private void updateGui(ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Npc person: personGroup) {
                    Direction curDir;
                    int newX, newY;
                    GameObject itemHit;
                    curDir = person.getDirection();
                    newX = person.getPosX() + curDir.getX();
                    newY = person.getPosY() + curDir.getY();
                    if (painter.wallCollision(newX, newY)) {
                        person.setDirection(Direction.getOpposite(person.getDirection())); 
                        curDir = person.getDirection();
                        newX = person.getPosX() + curDir.getX();
                        newY = person.getPosY() + curDir.getY();    
                    }
                    for (Room room : roomGroup) {
                        itemHit = painter.itemCollision(room, newX, newY);
                        if (itemHit != null) {
                            room.getInventory().exchangeItem(itemHit, person.getInventory(), "npcPickup");
                        }
                    }
                    changePos(person, newX, newY);                   
                }
                gui.setShowObjects(personGroup, roomGroup);
            }
        };
        if (paused) {
            timer.stop();
        }
        else {
            timer.start();
        }
       
    }

    private void changePos(Npc person, int newX, int newY) {
        person.setPosX(newX);
        person.setPosY(newY);   
        person.setCurRoom();

     //   int room = person.getCurRoom();
     //   System.out.println(person.npcName() + " is in room: " + room); 
    }
    
}
