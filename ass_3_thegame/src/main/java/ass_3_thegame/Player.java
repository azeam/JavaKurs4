package ass_3_thegame;

public class Player {
    String name = Constants.PLAYER_NAME;
    private Inventory inventory;
    private int posX, posY, curRoom;
    private GameObject selectedObject;

    public int getPosX() {
        return this.posX;
    }

    public synchronized void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public synchronized void setPosY(int posY) {
        this.posY = posY;
    }

    public Player() {
        this.inventory = new Inventory(Constants.INV_SIZE_PLAYER, Constants.INV_SIZE_PLAYER, "player", this.name, null);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getCurRoom() {
        return curRoom;
    }

    public void setCurRoom() {
        for (int i = 1; i <= Constants.NUM_ROOMS; i++) {
            if (this.posX < Constants.ROOM_WIDTH + Constants.MARGIN) {
                this.curRoom = 1;
            }
            else if (this.posX > Constants.ROOM_WIDTH * i + Constants.MARGIN && this.posX < Constants.ROOM_WIDTH * (i + 1) + Constants.MARGIN) {
                this.curRoom = i + 1;
            }
        }
    }

	public void setSelectedPlayerObject(GameObject selObj) {
        this.selectedObject = selObj;
    }
    
    public GameObject getSelectedPlayerObject() {
        return this.selectedObject;
    }

	public boolean isCarrying() {
		return (this.inventory.getInventory()[0] != null);
	}

}