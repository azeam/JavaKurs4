package ass_3_thegame;

public interface Npc {
    
    public String getName();
    public String showNpc();
    
    public Direction getDirection();
    public void setDirection(Direction direction);

    public int getPosX();
    public void setPosX(int i);

    public int getPosY();
    public void setPosY(int i);

    public int getCurRoom();
    public void setCurRoom();

    public Inventory getInventory();
    
	public boolean isCarrying();
}
