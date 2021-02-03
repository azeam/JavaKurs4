package ass_3_thegame;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Inventory {
    private GameObject[] inventory;

    public Inventory(int maxItems) {
        this.inventory = new GameObject[maxItems];
    }

    public GameObject[] getInventory() {
        return this.inventory;
    }

    public boolean addToInventory(GameObject[] inventory, GameObject gameObject) {
        List<GameObject> arrAsList = Arrays.asList(inventory);
        // make new list without nulls
        arrAsList = arrAsList
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
           
        if (arrAsList.size() < inventory.length) {
            inventory[arrAsList.size()] = gameObject;
            System.out.println("Added item to inventory, new inv: " + Arrays.toString(inventory));
            return true;
        }
        else {
            return false;
        }
    }

	public void exchangeItem(GameObject gameObject, Inventory otherInventory, String exchType, int newX, int newY) {
        GameObject otherObject = null;
        // define before replace or npc inventory will be already replaced
        if (exchType.equals("npcPickup")) {
            otherObject = otherInventory.getInventory()[0];
        }
        
        // add the collected object to npcs inventory if possible
        if (addToInventory(otherInventory.getInventory(), gameObject)) {
            replaceItem(otherObject, gameObject);
            gameObject.setPosX(newX);
            gameObject.setPosY(newY);    
        
            System.out.println("exchanged inventory " + Arrays.toString(this.inventory) + " with " + Arrays.toString(otherInventory.getInventory()));    
        }
	}

    private void replaceItem(GameObject otherObject, GameObject myObject) {
        List<GameObject> arrAsList = Arrays.asList(this.inventory);
        // find object collided with and exchange it with Npcs item
        arrAsList = arrAsList
            .stream()
            .map(x -> {
                if (x == myObject) {
                    return otherObject;
                }
                return x;
            })
            .collect(Collectors.toList());

//            System.out.println("after filtering: " + arrAsList);
            this.inventory = arrAsList.toArray(new GameObject[arrAsList.size()]);
    }


    /* 
        Ska innehålla en array av GameObjects. Arrayen ska vara an-
        passad efter det maxantal objekt som det kan bära (Npc bör bara
        kunna bära ett GameObjekt åt gången medan spelaren kan bära lite
        fler och rummen kan innehåla rätt många innan de blir “fulla”. Gi-
        vetvis säger programmet ifrån om man försöker placera fler saker än
        det finns plats för. Här ska mekaniken för att plocka upp, byta bort
        och lägga ned objekt hanteras. ALL HANTERING AV ARRAYERNA
        SKA SKE MED STREAMS - det är alltså INTE tillåtet att använda
        ArrayLists/LinkedLists eller andra (smartare) lösningar!
    */

}
