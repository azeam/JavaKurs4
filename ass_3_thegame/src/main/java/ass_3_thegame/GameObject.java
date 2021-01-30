package ass_3_thegame;

public class GameObject {
    // Ska hantera alla "icke-
    // levandeöbjekt i spelet (möbler, nycklar etc). GameObject ska innehålla
    // en boolean som avgör om objektet går att ta med sig eller är fastmonterat"i rummet.
    private boolean isPickable;
    private String type;

    public GameObject() {
        this.type = "Key";
    }

    public String getType() {
        return this.type;
    }

    @Override 
    public String toString() {
        return this.type;
    }

}
