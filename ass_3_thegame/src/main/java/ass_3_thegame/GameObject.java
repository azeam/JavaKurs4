package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

// TODO: must be abstract/interface
public abstract class GameObject {
    // Ska hantera alla "icke-
    // levandeöbjekt i spelet (möbler, nycklar etc). GameObject ska innehålla
    // en boolean som avgör om objektet går att ta med sig eller är fastmonterat"i rummet.
    protected boolean pickable;
    protected String type;
    private int posX, posY;
    private int id;

    public GameObject(Room room, int id) {
        // TODO: test this, item should not be placed where char is - should be fixed
        // only place within room bounds
        if (room != null) {
            int roomNumber = room.getRoomId();    
                do {
                    this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.ROOM_WIDTH * roomNumber + Constants.OBJ_SIZE, Constants.ROOM_WIDTH * (roomNumber + 1) - Constants.OBJ_SIZE + Constants.MARGIN);
                    this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.OBJ_SIZE, Constants.ROOM_HEIGHT - Constants.OBJ_SIZE + Constants.MARGIN); 
                } while (this.posX > Constants.MARGIN + Constants.ROOM_WIDTH / 2 - Constants.PLAYER_WIDTH &&
                            this.posX < Constants.MARGIN + Constants.ROOM_WIDTH / 2 + Constants.PLAYER_WIDTH && 
                            this.posY > Constants.MARGIN + Constants.ROOM_HEIGHT / 2 - Constants.PLAYER_HEIGHT &&
                            this.posY < Constants.MARGIN + Constants.ROOM_HEIGHT / 2 + Constants.PLAYER_HEIGHT);
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
        return this.type + this.id;
    }

}
