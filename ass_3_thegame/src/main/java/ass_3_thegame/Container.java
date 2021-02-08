package ass_3_thegame;

public class Container extends GameObject {

    public Container(Room room) {
        super(room);
        this.pickable = false;
        this.type = "Chest";
    }
    // En subklass till GameObject som har ett Inventory. Kan vara låst eller öppet.
   
}
