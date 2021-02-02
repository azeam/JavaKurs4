package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public class GameObject {
    // Ska hantera alla "icke-
    // levandeöbjekt i spelet (möbler, nycklar etc). GameObject ska innehålla
    // en boolean som avgör om objektet går att ta med sig eller är fastmonterat"i rummet.
    private boolean isPickable;
    private String type;
    private int posX, posY;


    public GameObject() {
        this.type = "Key";
        this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + 1, Constants.ALL_ROOMS_WIDTH - 1 + Constants.MARGIN);
        this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + 1, Constants.ROOM_HEIGHT - 1 + Constants.MARGIN);    
    }

    public String getType() {
        return this.type;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    @Override 
    public String toString() {
        return this.type;
    }

}
