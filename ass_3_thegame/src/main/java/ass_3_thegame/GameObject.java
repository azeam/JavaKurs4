package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public abstract class GameObject {
   
    protected boolean pickable;
    protected String type;
    private int posX, posY;
    protected int id;

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

    public int getId() {
        return this.id;
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

    public synchronized void setPosX(int x) {
        this.posX = x;
    }

    public synchronized void setPosY(int y) {
        this.posY = y;
    }

    @Override 
    public String toString() {
        if (this.type == "Key") {
            Key key = (Key) this;
            if (key.isMaster()) {
                return "Master key";
            }
        }
        return this.type + this.id;
    }

}
