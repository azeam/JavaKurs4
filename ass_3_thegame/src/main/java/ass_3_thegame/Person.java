package ass_3_thegame;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import ass_3_thegame.factories.GameObjectFactory;

 /* 
        En person är Npc - dessa ska lagras i lista av något slag och
        ha ett eget liv så till vida att de ska hanteras concurrent. Npc:ernas
        beteende bestäms av slumptal. Det rör sig mellan rummen, plockar upp,
        lägger ned saker. Det ska finnas en showPerson, som visar personens
        namn och vad denne bär på. Vill man ha något som personen bär på så
        kan man antingen följa efter och vänta på att objekten läggs ned eller
        be om att byta mot ett objekt i det egna inventory.
    */

public class Person implements Npc {
    private int posX, posY, curRoom;
    private Inventory inventory;
    private Direction direction;
    private String npcName;
    private Painter painter;

    public Person(String name, Painter painter) {
        this.npcName = name;
        this.painter = painter;
        this.inventory = new Inventory(Constants.INV_SIZE_NPC_MIN, Constants.INV_SIZE_NPC, "npc", name);
        setStartPosition();
        setCurRoom();
    }

    private void setStartPosition() {
        // TODO: does not seem to work, check why - should be fixed, confirm
        do {
            System.out.println(this.npcName + " set position to " + this.posX + this.posY);
            this.posX = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.NPC_WIDTH, Constants.ALL_ROOMS_WIDTH - Constants.NPC_WIDTH + Constants.MARGIN);
            this.posY = ThreadLocalRandom.current().nextInt(Constants.MARGIN + Constants.NPC_HEIGHT, Constants.ROOM_HEIGHT - Constants.NPC_HEIGHT + Constants.MARGIN);            
        } while (painter.wallCollision(this.posX, this.posY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT) || painter.getHitItem(this.posX, this.posY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT)[0] != null);
        direction = Direction.getRandom();
    }

    @Override
    public boolean isCarrying() {
        return (this.inventory.getInventory()[0] != null);
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
    public String getName() {
        return this.npcName;
    }

    @Override
    public String showNpc() {
        return ("NPC " + this.npcName + " at position " + this.posX + ", " + this.posY + " with inventory " + this.inventory);
    }

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

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

}
