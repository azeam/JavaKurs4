package ass_3_thegame;

import java.util.ArrayList;
import java.util.Arrays;

import ass_3_thegame.factories.GameObjectFactory;

public class Player {
    // Håller ordning på var spelaren befinner sig (alltså vilket rum
    // som ska beskrivas), samt spelarens Inventory.

    String name = "Azeam";
    Inventory inventory;

    public Player() {
        Inventory inv = new Inventory(Constants.INV_SIZE_PLAYER);
        GameObjectFactory gameObjectFactory = new GameObjectFactory();
        ArrayList<GameObject> gameObjectGroup = gameObjectFactory.createGroup(0, true);
        for (GameObject obj: gameObjectGroup) {
            inv.addToInventory(inv, obj); 
        }
        System.out.println("Inventory of player: " + Arrays.toString(inv.getInventory()));
        this.inventory = inv;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

}