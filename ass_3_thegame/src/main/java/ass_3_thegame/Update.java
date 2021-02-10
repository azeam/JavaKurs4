package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ass_3_thegame.factories.GameObjectFactory;
import ass_3_thegame.factories.NpcFactory;
import ass_3_thegame.factories.RoomFactory;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class Update implements Runnable {

    private Gui gui;
    private Names names = new Names();
    private NpcFactory npcFactory = new NpcFactory();
    private RoomFactory roomFactory = new RoomFactory();
    private ArrayList<Room> roomGroup;
    private List<String> namesList;
    private ArrayList<Npc> personGroup;
    private Player player;

    public Update(Gui gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    private void placeMasterKey(ArrayList<Room> roomGroup2) {
        // make list of all chests
        List<GameObject[]> roomsInventories = new ArrayList<GameObject[]>();
        for (Room room: roomGroup) {
            roomsInventories.add(room.getInventory().getInventory());
        }
        List<Container> containers = new ArrayList<Container>();
        for (GameObject[] inventory : roomsInventories) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] instanceof Container) {
                    containers.add((Container) inventory[i]);
                }
            }
        }

        // make master key and place it in a random chest, the other chests are empty
        if (containers.size() > 0) {
            int masterPos = ThreadLocalRandom.current().nextInt(0, containers.size());
            GameObjectFactory gameObjectFactory = new GameObjectFactory();
            GameObject masterKey = gameObjectFactory.createGameObject(true, null, true);
            containers.get(masterPos).getInventory().addToInventory(containers.get(masterPos).getInventory(), masterKey);
        }

    }

    @Override
    public void run() {
        roomGroup = roomFactory.createGroup(Constants.NUM_ROOMS);
        gui.setRoomGroup(roomGroup);
        placeMasterKey(roomGroup);
        Platform.runLater(() -> {
            gui.setUpWallsAndLabels(); // set up walls and items before persons to check for wall collision
            gui.setUpInventory(this.player.getInventory(), this.player);
            gui.setUpItems();    
        });
        
        namesList = names.getRandomNames(Constants.NUM_NPCS);
        personGroup = npcFactory.createGroup("Person", Constants.NUM_NPCS, namesList, gui);
        
        Platform.runLater(() -> {
            gui.setUpPerson(personGroup);
        });
        updateGui(personGroup, roomGroup);
    }

    // get npc position and pass it to gui to draw, also checks for collisions and handles item pickup/drop
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
                            if (person.isCarrying()) {
                                if (player.isCarrying()) {
                                    gui.showMessage("Click on the key to trade with " + person.getName());
                                }
                                else {
                                    gui.showMessage("Find another key to trade with " + person.getName());
                                }
                                Constants.GL_NPC_HIT = person;
                            }
                            Constants.GL_PAUSED = true;
                        }
                        else if (gui.wallCollision(newX, newY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT)) {
                            person.setDirection(Direction.getOpposite(person.getDirection()));
                        }                     
                        else if (ThreadLocalRandom.current().nextInt(1, 1000) == 1 && person.isCarrying()) { // 1/1000 chance to drop item (seems ok number for default 60 FPS)
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
        gui.setTimer(timer);
    }

    private synchronized void changePos(Npc person, int newX, int newY) {
        person.setPosX(newX);
        person.setPosY(newY);   
        person.setCurRoom();
        
        int room = person.getCurRoom();
     //   System.out.println(person.getName() + " is in room: " + room); 
    }
    
}
