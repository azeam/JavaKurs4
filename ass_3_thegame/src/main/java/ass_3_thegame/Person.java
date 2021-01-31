package ass_3_thegame;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import ass_3_thegame.factories.GameObjectFactory;

public class Person implements Npc {
    int posX;
    int posY;
    int curRoom;
    Inventory inventory;
    Direction direction;
    String npcName;

    public Person(String name) {

        Inventory inv = new Inventory(Constants.INV_SIZE_NPC);
        int randomNumItems = ThreadLocalRandom.current().nextInt(Constants.INV_SIZE_NPC_MIN, Constants.INV_SIZE_NPC + 1);
        GameObjectFactory gameObjectFactory = new GameObjectFactory();
        ArrayList<GameObject> gameObjectGroup = gameObjectFactory.createGroup(randomNumItems, true);
        for (GameObject obj: gameObjectGroup) {
            inv.addToInventory(obj); 
        }
        
        this.inventory = inv;

        this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + 1, Constants.ALL_ROOMS_WIDTH - 1 + Constants.MARGIN);
        this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + 1, Constants.ROOM_HEIGHT - 1 + Constants.MARGIN);
        this.npcName = name;    
        direction = Direction.getRandom();
        if (this.posX < Constants.ROOM_WIDTH) {
            this.curRoom = 1;
        }
        else if (this.posX < Constants.ROOM_WIDTH * 2) {
            this.curRoom = 2;
        }
        else if (this.posX < Constants.ROOM_WIDTH * 3) {
            this.curRoom = 3;
        }
        else {
            this.curRoom = 4;
        }
    }

    @Override
    public boolean isCarrying() {
        return (this.inventory.getInventory()[0] == null);
    }


    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public String npcName() {
        return this.npcName;
    }

    @Override
    public String showNpc() {
        return ("NPC " + this.npcName + " at position " + this.posX + ", " + this.posY + " with inventory " + this.inventory);
    }
    /* 
        En person är Npc - dessa ska lagras i lista av något slag och
        ha ett eget liv så till vida att de ska hanteras concurrent. Npc:ernas
        beteende bestäms av slumptal. Det rör sig mellan rummen, plockar upp,
        lägger ned saker. Det ska finnas en showPerson, som visar personens
        namn och vad denne bär på. Vill man ha något som personen bär på så
        kan man antingen följa efter och vänta på att objekten läggs ned eller
        be om att byta mot ett objekt i det egna inventory.
    */

    @Override
    public Direction getDirection() {
        return this.direction;
    }
   
    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int getCurRoom() {
        return curRoom;
    }

    @Override
    public void setCurRoom(int room) {
        this.curRoom = room;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
