package ass_3_thegame;

public interface Npc {
    //Ska vara antingen ett Interface eller en Abstrakt klass och ge
    // ramarna för våra Npc:er. I grundstrukturen är det en abstract men
    // det är okej att byta. Tänk bara på att byta extends mot implements i subklasserna.
    public String npcName();
    public String showNpc();
    public int npcHP();
    public int getPosX();
    public int getPosY();
	public void setPosX(int i);
	public void setPosY(int i);
}
