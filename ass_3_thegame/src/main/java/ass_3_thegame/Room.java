package ass_3_thegame;

public class Room {
    // Ett Room ska ha ett unikt namn, ett Inventory och sen show-Metod() som beskriver det för spelaren.
    String roomName;
    Inventory inventory;
    int roomId;

    public Room(String roomName, Inventory inventory, int roomId) {
        this.roomName = roomName;
        this.inventory = inventory;
        this.roomId = roomId;
    }

    public void showRoom() {
        System.out.println("Room name: " + roomName);
        System.out.println("Room inventory: " + inventory);        
    }
}