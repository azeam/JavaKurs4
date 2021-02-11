package ass_3_thegame.factories;

import java.util.ArrayList;
import java.util.List;

import ass_3_thegame.Gui;
import ass_3_thegame.Npc;
import ass_3_thegame.Person;

public class NpcFactory {

    public Npc createNpc(String name, Gui gui) {
        return new Person(name, gui);
    }

	public ArrayList<Npc> createGroup(int number, List<String> names, Gui gui) {
        ArrayList<Npc> group = new ArrayList<Npc>();
        for (int i=0; i<number; i++) {
            String name = names.get(i);
            group.add(createNpc(name, gui));
        }
        return group;
	}
    
}
