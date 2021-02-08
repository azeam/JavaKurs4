package ass_3_thegame;

public class Container extends GameObject {

    private boolean open;
    private Inventory inventory;

    public Container(Room room, int id, boolean containsKey) {
        super(room, id);
        this.pickable = false;
        this.type = "Chest";
        this.open = false;
        if (containsKey) {
            this.inventory = new Inventory(1, 1, "container", "chest in room " + room.getRoomId(), room);
        }
        else {
            this.inventory = new Inventory(0, 0, "container", "chest in room " + room.getRoomId(), room);
        }
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
