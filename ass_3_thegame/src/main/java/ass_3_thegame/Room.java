package ass_3_thegame;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import ass_3_thegame.factories.GameObjectFactory;

public class Room {
    // Ett Room ska ha ett unikt namn, ett Inventory och sen show-Metod() som beskriver det för spelaren.
    private String roomName;
    private Inventory inventory;
    private int roomId;

    public Room(int roomId) {
        this.roomName = "Room" + roomId;
        int randomNumItems = ThreadLocalRandom.current().nextInt(Constants.INV_SIZE_ROOM_MIN, Constants.INV_SIZE_ROOM + 1);
        Inventory inv = new Inventory(Constants.INV_SIZE_ROOM);
        GameObjectFactory gameObjectFactory = new GameObjectFactory();
        ArrayList<GameObject> gameObjectGroup = gameObjectFactory.createGroup(randomNumItems, false);
        for (GameObject obj: gameObjectGroup) {
            inv.addToInventory(inv, obj); 
        }
        System.out.println("Inventory size of room: " + inv.getInventory().length);
        this.inventory = inv;
        this.roomId = roomId;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void showRoom() {
        System.out.println("Room name: " + roomName);
        System.out.println("Room inventory: " + inventory);        
    }
}