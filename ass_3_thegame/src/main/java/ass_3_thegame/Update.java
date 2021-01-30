package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

public class Update implements Runnable {

    // Ska implementera Runnable och starta en tråd som Regelbundet updaterar Guit
    // utifrån vad som händer i spelet.
    Gui gui;
    ArrayList<Npc> personGroup = new ArrayList<>();

    public Update(Gui gui) {
        this.gui = gui;
	}

	@Override
    public void run() {
        Names names = new Names();
        NpcFactory npcFactory = new NpcFactory();

        List<String> namesList = names.getRandomNames(5);
        personGroup = npcFactory.createGroup("Person", 5, namesList);
        updateNpcPos();
    }

    private void updateNpcPos() {

        while (true) {
            try {
                for (Npc person: personGroup) {
                    // get random new direction, should only be done when hitting a wall
                    Direction randomDir = Direction.getRandom(); 
                    // move person
                    person.setPosX(person.getPosX() + randomDir.getX()); 
                    person.setPosY(person.getPosY() + randomDir.getY());
                    person.showNpc();
                    gui.setShowPersons(person);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        
        
    }
}
