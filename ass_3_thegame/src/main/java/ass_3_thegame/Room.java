package ass_3_thegame;

public class Room {
    // Ett Room ska ha ett unikt namn, ett Inventory och sen show-Metod() som beskriver det för spelaren.
    private String roomName;
    private Inventory inventory;
    private int roomId;

    public Room(int roomId) {
        this.roomId = roomId;
        this.roomName = "Room " + (roomId + 1);
        this.inventory = new Inventory(Constants.INV_SIZE_ROOM_MIN, Constants.INV_SIZE_ROOM, "room", this.roomName, this);
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