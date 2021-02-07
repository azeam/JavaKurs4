package ass_3_thegame;

public class Player {
    // Håller ordning på var spelaren befinner sig (alltså vilket rum
    // som ska beskrivas), samt spelarens Inventory.

    String name = Constants.PLAYER_NAME;
    private Inventory inventory;
    private int posX, posY;

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Player() {
        this.inventory = new Inventory(Constants.INV_SIZE_PLAYER, Constants.INV_SIZE_PLAYER, "player");
    }

    public Inventory getInventory() {
        return this.inventory;
    }


}