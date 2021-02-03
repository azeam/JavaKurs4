package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public class GameObject {
    // Ska hantera alla "icke-
    // levandeöbjekt i spelet (möbler, nycklar etc). GameObject ska innehålla
    // en boolean som avgör om objektet går att ta med sig eller är fastmonterat"i rummet.
    private boolean pickable;
    private String type;
    private int posX, posY;


    public GameObject() {
        this.type = "Key";
        this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.OBJ_SIZE, Constants.ALL_ROOMS_WIDTH - Constants.OBJ_SIZE + Constants.MARGIN);
        this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.OBJ_SIZE, Constants.ROOM_HEIGHT - Constants.OBJ_SIZE + Constants.MARGIN); 
        this.pickable = true;   
    }

    public String getType() {
        return this.type;
    }

    public boolean isPickable() {
        return this.pickable;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosX(int x) {
        this.posX = x;
    }

    public void setPosY(int y) {
        this.posY = y;
    }

    @Override 
    public String toString() {
        return this.type;
    }

}
