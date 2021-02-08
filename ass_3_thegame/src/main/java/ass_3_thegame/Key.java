package ass_3_thegame;

public class Key extends GameObject {

    public Key(Room room) {
        super(room);
        this.pickable = true;  
        this.type = "Key";
        // TODO Auto-generated constructor stub
    }
    // En subklass till GameObject vars objekt används för att låsa
    // upp Containers. Välj om keyobjektet ska hålla koll på vilken instans
    // av container den passar till eller tvärtom!
}
