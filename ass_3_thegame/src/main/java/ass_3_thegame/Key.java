package ass_3_thegame;

public class Key extends GameObject {
    private boolean master;
    
    public Key(Room room, int id, boolean master) {
        super(room, id);
        this.pickable = true;  
        this.type = "Key";
        this.master = master;
        System.out.println(master);
    }

    public boolean isMaster() {
        return this.master;
    }

    // En subklass till GameObject vars objekt används för att låsa
    // upp Containers. Välj om keyobjektet ska hålla koll på vilken instans
    // av container den passar till eller tvärtom!
}
