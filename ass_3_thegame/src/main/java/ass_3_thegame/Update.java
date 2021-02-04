package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

        Player player = new Player();

        gui.setUpWalls(painter, roomGroup);
        gui.setUpPerson(painter, personGroup);
        gui.setUpItems(painter, roomGroup);
        updateGui(personGroup, roomGroup);
    }

    private void updateGui(ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Direction curDir;
                int newX, newY;
                Room room;
                for (Npc person : personGroup) {
                    room = roomGroup.get(person.getCurRoom() - 1);
                    curDir = person.getDirection();
                    newX = person.getPosX() + curDir.getX();
                    newY = person.getPosY() + curDir.getY();

                    // TODO: not working well - sometimes not possible to pick up (problem is in hit
                    // detection but I don't see why)
//                    System.out.println("itemhit is " + itemHit);
                    if (gui.itemCollision(painter, person, room, newX, newY)) {}
                    else if (painter.wallCollision(newX, newY)) {
                        person.setDirection(Direction.getOpposite(person.getDirection()));
                    }                     
                    else if (ThreadLocalRandom.current().nextInt(1, 1000) == 1 && person.isCarrying()) {
                        Direction behind = (person.getDirection());
                        int behindX = person.getPosX() - behind.getX() * Constants.OBJ_SIZE;
                        int behindY = person.getPosY() - behind.getY() * Constants.OBJ_SIZE;
                        System.out.println(person.npcName() + " attempting drop off at " + behindX + " " + behindY);
                        GameObject item = person.getInventory().getInventory()[0];
                        if (person.getInventory().exchangeItem(person.getInventory().getInventory()[0], room.getInventory(),
                                "npcDropoff", behindX, behindY)) {
                                    gui.addItem(painter, item);
                                }
                    }
                    else {
                        changePos(person, newX, newY);
                    }
                }
                gui.setShowObjects(painter, personGroup, roomGroup);
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

        int room = person.getCurRoom();
     //   System.out.println(person.npcName() + " is in room: " + room); 
    }
    
}
