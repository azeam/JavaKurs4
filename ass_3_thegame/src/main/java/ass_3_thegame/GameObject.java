package ass_3_thegame;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// TODO: must be abstract/interface
public class GameObject {
    // Ska hantera alla "icke-
    // levandeöbjekt i spelet (möbler, nycklar etc). GameObject ska innehålla
    // en boolean som avgör om objektet går att ta med sig eller är fastmonterat"i rummet.
    private boolean pickable;
    private String type;
    private int posX, posY;

    public GameObject(boolean onlyPickable) {
        if (onlyPickable) {
            this.type = "Key";
        }
        else {
            String [] types = {"Key", "Chest", "Furniture"};
            Random random = new Random();
            int select = random.nextInt(types.length); 
            this.type = types[select];    
        }

        // TODO: test this, item should not be placed where char is - should be fixed
        do {
            this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.OBJ_SIZE, Constants.ALL_ROOMS_WIDTH - Constants.OBJ_SIZE + Constants.MARGIN);
            this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.OBJ_SIZE, Constants.ROOM_HEIGHT - Constants.OBJ_SIZE + Constants.MARGIN); 
        } while (this.posX > Constants.MARGIN + Constants.ROOM_WIDTH / 2 - Constants.PLAYER_WIDTH &&
                 this.posX < Constants.MARGIN + Constants.ROOM_WIDTH / 2 + Constants.PLAYER_WIDTH && 
                 this.posY > Constants.MARGIN + Constants.ROOM_HEIGHT / 2 - Constants.PLAYER_HEIGHT &&
                 this.posY < Constants.MARGIN + Constants.ROOM_HEIGHT / 2 + Constants.PLAYER_HEIGHT);
        

        if (this.type == "Key") {
            this.pickable = true;  
        }
        else {
            this.pickable = false;
        } 
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
