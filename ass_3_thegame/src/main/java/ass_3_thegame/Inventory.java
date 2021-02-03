package ass_3_thegame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Inventory {
    private GameObject[] inventory;
    private int maxItems;

    public int getMaxItems() {
        return this.maxItems;
    }

    public Inventory(int maxItems) {
        this.inventory = new GameObject[maxItems];
        this.maxItems = maxItems;
    }

    public GameObject[] getInventory() {
        return this.inventory;
    }

    public void setInventory(GameObject[] inventory) {
        this.inventory = inventory;
    }




    // TODO: skicka Inventory
    // hämta maxsize
    // gör ny array med maxsize
    // hämta befintlig -> modifiera
    // sätt ny till Inventory


    public boolean addToInventory(Inventory inv, GameObject gameObject) {
        GameObject[] objArray = inv.getInventory();
        GameObject[] newObjArray = new GameObject[inv.getMaxItems()];
        List<GameObject> arrAsList = Arrays.asList(objArray);
        System.out.println("Unsorted inv: " + Arrays.toString(objArray));
        // make new list without nulls
        newObjArray = arrAsList
            .stream()
            .sorted(Comparator.nullsLast(Comparator.comparing(GameObject::getType, Comparator.nullsLast(Comparator.reverseOrder()))))
            .toArray(GameObject[]::new);

        List<GameObject> arrAsListCopy = arrAsList
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
           
        if (arrAsListCopy.size() < objArray.length) {
            newObjArray[arrAsListCopy.size()] = gameObject;
            // setting inv[i] will modify the passed in object, updating it via streams does not
            // TODO: inv can grow indefinitely, why?!
            inv.setInventory(newObjArray);
            System.out.println("list size " + arrAsList.size() + " list copy size " + arrAsListCopy.size());
            System.out.println("Unsorted: " + arrAsList);
            System.out.println("Sorted: " + Arrays.toString(objArray));
            System.out.println("Added item to inventory, new inv: " + Arrays.toString(objArray));
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
        if (addToInventory(otherInventory, gameObject)) {
            replaceItem(otherObject, gameObject);
            gameObject.setPosX(newX);
            gameObject.setPosY(newY);    
        
            System.out.println("exchanged inventory " + Arrays.toString(this.inventory) + " with " + Arrays.toString(otherInventory.getInventory()));    
        }
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
