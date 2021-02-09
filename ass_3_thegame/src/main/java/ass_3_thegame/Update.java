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
    Names names = new Names();
    NpcFactory npcFactory = new NpcFactory();
    RoomFactory roomFactory = new RoomFactory();
    private ArrayList<Room> roomGroup;
    private List<String> namesList;
    private ArrayList<Npc> personGroup;

    public Update(Gui gui, Player player) {
        this.gui = gui;
        roomGroup = roomFactory.createGroup(Constants.NUM_ROOMS);
        gui.setRoomGroup(roomGroup);

        gui.setUpWalls(); // set up walls and items before persons to check for wall collision
        gui.setUpInventory(player.getInventory(), player);
        gui.setUpItems();
        
        namesList = names.getRandomNames(Constants.NUM_NPCS);
        personGroup = npcFactory.createGroup("Person", Constants.NUM_NPCS, namesList, gui);
        
        gui.setUpPerson(personGroup);
        
    }

    @Override
    public void run() {
        updateGui(personGroup, roomGroup);
    }

    private void updateGui(ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        // will safely run on java fx thread
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!Constants.GL_PAUSED) {
                    gui.hideInventory(false); // hide inventory when not trading
                    Direction curDir;
                    int newX, newY;
                    Room room;
                    Constants.GL_NPC_HIT = null;
                    for (Npc person : personGroup) {
                        
                        room = roomGroup.get(person.getCurRoom() - 1);
                        curDir = person.getDirection();
                        newX = person.getPosX() + curDir.getX();
                        newY = person.getPosY() + curDir.getY();
                            
                        if (gui.npcItemCollision(person, room, newX, newY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT)) {}
                        else if (gui.playerNpcCollision(person, newX, newY)) {
                            gui.setUpInventory(person.getInventory(), person);
                            if (person.getInventory().getInventory()[0] != null) {
                                Constants.GL_NPC_HIT = person;
                            }
                            Constants.GL_PAUSED = true;
                        }
                        else if (gui.wallCollision(newX, newY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT)) {
                            person.setDirection(Direction.getOpposite(person.getDirection()));
                        }                     
                        else if (ThreadLocalRandom.current().nextInt(1, 1000) == 1 && person.isCarrying()) {
                            Direction behind = (person.getDirection());
                            
                            int behindX = person.getPosX() - behind.getX() * Constants.OBJ_SIZE;
                            int behindY = person.getPosY() - behind.getY() * Constants.OBJ_SIZE;
                            
                            GameObject item = person.getInventory().getInventory()[0];
                            if (!gui.wallCollision(behindX, behindY, Constants.OBJ_SIZE, Constants.OBJ_SIZE) && person.getInventory().exchangeItem(item, room.getInventory(), "npcDropoff", behindX, behindY)) {
                                gui.addItem(item);
                            }
                        }
                        else {
                            changePos(person, newX, newY);
                        }
                    }
                    gui.paint(personGroup);
                }   
            }
        };
        timer.start();       
    }

    private synchronized void changePos(Npc person, int newX, int newY) {
        person.setPosX(newX);
        person.setPosY(newY);   
        person.setCurRoom();
        
        int room = person.getCurRoom();
     //   System.out.println(person.getName() + " is in room: " + room); 
    }
    
}
