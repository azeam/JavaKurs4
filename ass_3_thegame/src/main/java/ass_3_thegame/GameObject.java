package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public abstract class GameObject {
   
    protected boolean pickable;
    protected String type;
    private int posX, posY;
    protected int id;

    public GameObject(Room room, int id) {
        // only place within room bounds and not on top of player
        if (room != null) {
            int roomNumber = room.getRoomId();    
                do {
                    // set some margins near room openings to prevent getting stuck in room
                    this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.ROOM_WIDTH * roomNumber + Constants.OBJ_SIZE * 2, 
                                                                    Constants.ROOM_WIDTH * (roomNumber + 1) - Constants.OBJ_SIZE * 2 + Constants.MARGIN);
                    this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.OBJ_SIZE * 2, 
                                                                    Constants.ROOM_HEIGHT - Constants.OBJ_SIZE * 2 + Constants.MARGIN); 
                } while ((this.posX + Constants.OBJ_SIZE > Constants.MARGIN + Constants.ROOM_WIDTH / 2 - Constants.PLAYER_WIDTH * 2 &&
                        this.posX + Constants.OBJ_SIZE < Constants.MARGIN + Constants.ROOM_WIDTH / 2 + Constants.PLAYER_WIDTH * 2) && 
                        (this.posY + Constants.OBJ_SIZE > Constants.MARGIN + Constants.ROOM_HEIGHT / 2 - Constants.PLAYER_HEIGHT * 2 &&
                        this.posY + Constants.OBJ_SIZE < Constants.MARGIN + Constants.ROOM_HEIGHT / 2 + Constants.PLAYER_HEIGHT * 2));
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
                return "Door";
            }
        }
        return this.type + this.id;
    }

}
