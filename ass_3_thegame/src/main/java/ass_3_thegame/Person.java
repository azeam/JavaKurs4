package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public class Person implements Npc {
    int hp;
    int posX;
    int posY;
    String npcName;

    public Person(String name) {
        this.hp = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        this.posX = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        this.posY = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        this.npcName = name;        
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
    public int npcHP() {
        return this.hp;
    }

    @Override
    public String showNpc() {
        return ("NPC " + this.npcName + " with hp: " + this.hp + " at position" + this.posX + ", " + this.posY);
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
   
}
