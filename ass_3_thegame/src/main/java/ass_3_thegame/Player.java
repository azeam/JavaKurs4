package ass_3_thegame;

public class Player {
    // Håller ordning på var spelaren befinner sig (alltså vilket rum
    // som ska beskrivas), samt spelarens Inventory.

    String name = Constants.PLAYER_NAME;
    private Inventory inventory;

    public Player() {
        this.inventory = new Inventory(Constants.INV_SIZE_PLAYER, Constants.INV_SIZE_PLAYER, "player");
    }

    public Inventory getInventory() {
        return this.inventory;
    }

}