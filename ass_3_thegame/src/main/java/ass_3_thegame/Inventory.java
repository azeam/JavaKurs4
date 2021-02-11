package ass_3_thegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import ass_3_thegame.factories.GameObjectFactory;

public class Inventory {
    private GameObject[] inventory;
    private int maxItems;
    private String ownerName;

    public int getMaxItems() {
        return this.maxItems;
    }

    public Inventory(int min, int max, String owner, String ownerName, Room room) {
        this.inventory = new GameObject[max];
        this.maxItems = max;
        this.ownerName = ownerName;
        setStartInventory(min, max, owner, room);
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public GameObject[] getInventory() {
        return this.inventory;
    }

    public void setInventory(GameObject[] inventory) {
        this.inventory = inventory;
    }

    public void setStartInventory(int min, int max, String owner, Room room) {
        int randomNumItems = ThreadLocalRandom.current().nextInt(min, max + 1);
        GameObjectFactory gameObjectFactory = new GameObjectFactory();
        ArrayList<GameObject> gameObjectGroup = null;

        switch (owner) {
            case "player":      gameObjectGroup = gameObjectFactory.createGroup(0, true, null); break;
            case "room":        gameObjectGroup = gameObjectFactory.createGroup(randomNumItems, false, room); break;
            case "npc":         gameObjectGroup = gameObjectFactory.createGroup(randomNumItems, true, null); break;
            case "container":   gameObjectGroup = gameObjectFactory.createGroup(0, true, null); break;
            default: break;
        }
        for (GameObject obj: gameObjectGroup) {
            addToInventory(this, obj); 
        }
	}

    public boolean addToInventory(Inventory inv, GameObject gameObject) {
        GameObject[] objArray = inv.getInventory();
        GameObject[] newObjArray = new GameObject[inv.getMaxItems()];
        List<GameObject> arrAsList = Arrays.asList(objArray);
        // sort list with nulls last
        newObjArray = arrAsList
            .stream()
            .sorted(Comparator.nullsLast(Comparator.comparing(GameObject::getType, Comparator.nullsLast(Comparator.reverseOrder()))))
            .toArray(GameObject[]::new);

        // make list without nulls
        List<GameObject> arrAsListCopy = arrAsList
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        // check if there's room in inventory (non null list < sorted list), add object to first free space
        if (arrAsListCopy.size() < objArray.length) {
            newObjArray[arrAsListCopy.size()] = gameObject;
            inv.setInventory(newObjArray);
            return true;
        }
        else {
            return false;
        }
    }

	public boolean exchangeItem(GameObject gameObject, Inventory otherInventory, String exchType, int newX, int newY) {
        GameObject otherObject = null;
        // define before replace or npc inventory will be already replaced
        if (exchType.equals("npcPickup")) {
            otherObject = otherInventory.getInventory()[0];
        }
        
        // add the collected object to npcs inventory if possible
        if (addToInventory(otherInventory, gameObject)) {
            replaceItem(otherObject, gameObject);
            gameObject.setPosX(newX);
            gameObject.setPosY(newY);    
            return true;
        }
        return false;
	}

    private void replaceItem(GameObject otherObject, GameObject myObject) {
        List<GameObject> arrAsList = Arrays.asList(this.inventory);
        // find object collided with and exchange it with Npcs item
        this.inventory = arrAsList
            .stream()
            .map(x -> {
                if (x == myObject) {
                    return otherObject;
                }
                return x;
            })
            .sorted(Comparator.nullsLast(Comparator.comparing(GameObject::getType, Comparator.nullsLast(Comparator.reverseOrder()))))
            .toArray(GameObject[]::new);
    }

    // remove from inventory
	public void remove(GameObject selected) {
        List<GameObject> arrAsList = Arrays.asList(this.inventory);
        this.inventory = arrAsList
            .stream()
            .map(x -> {
                if (x == selected) {
                    return null;
                }
                return x;
            })
            .sorted(Comparator.nullsLast(Comparator.comparing(GameObject::getType, Comparator.nullsLast(Comparator.reverseOrder()))))
            .toArray(GameObject[]::new);
	}

}
