package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public class Person implements Npc {
    int posX;
    int posY;
    int curRoom;
    Direction direction;
    String npcName;

    public Person(String name) {
        this.posX = ThreadLocalRandom.current().nextInt(1, 800 + 1);
        this.posY = ThreadLocalRandom.current().nextInt(1, 200 + 1);
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
        return ("NPC " + this.npcName + " at position " + this.posX + ", " + this.posY);
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
}
