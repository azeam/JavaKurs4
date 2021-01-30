package ass_3_thegame;

public interface Npc {
    //Ska vara antingen ett Interface eller en Abstrakt klass och ge
    // ramarna för våra Npc:er. I grundstrukturen är det en abstract men
    // det är okej att byta. Tänk bara på att byta extends mot implements i subklasserna.
    public String npcName();
    public String showNpc();
    
    public Direction getDirection();
    public void setDirection(Direction direction);

    public int getPosX();
    public void setPosX(int i);

    public int getPosY();
    public void setPosY(int i);

    public int getCurRoom();
    public void setCurRoom(int room);
}
