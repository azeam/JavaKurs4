package ass_3_thegame;

public class Key extends GameObject {
    private boolean master;
    
    public Key(Room room, int id, boolean master) {
        super(room, id);
        this.pickable = true;  
        this.type = "Key";
        this.id = id;
        this.master = master;
    }

    public boolean isMaster() {
        return this.master;
    }

}
