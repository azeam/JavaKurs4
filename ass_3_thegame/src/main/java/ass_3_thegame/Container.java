package ass_3_thegame;

public class Container extends GameObject {

    private boolean open;
    private Inventory inventory;

    public Container(Room room, int id) {
        super(room, id);
        this.id = id;
        this.pickable = false;
        this.type = "Chest";
        this.open = false;
        this.inventory = new Inventory(1, 1, "container", "chest " + this.id + " in room " + (room.getRoomId() + 1), room);
    }
    // En subklass till GameObject som har ett Inventory. Kan vara låst eller öppet.
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return this.open;
    }

}
