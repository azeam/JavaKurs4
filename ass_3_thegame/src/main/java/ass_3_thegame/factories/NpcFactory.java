package ass_3_thegame.factories;

import java.util.ArrayList;
import java.util.List;

import ass_3_thegame.Npc;
import ass_3_thegame.Painter;
import ass_3_thegame.Person;

public class NpcFactory {

    public Npc createNpc(String type, String name, Painter painter) {
        if (type.equals("Person")) {
            return new Person(name, painter);
        }
        else {
            return null;
        }
    }

	public ArrayList<Npc> createGroup(String type, int number, List<String> names, Painter painter) {
        ArrayList<Npc> group = new ArrayList<Npc>();
        for (int i=0; i<number; i++) {
            String name = names.get(i);
            group.add(createNpc(type, name, painter));
        }
        return group;
	}
    
}
