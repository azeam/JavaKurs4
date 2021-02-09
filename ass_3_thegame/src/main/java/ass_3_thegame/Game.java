package ass_3_thegame;

import javafx.stage.Stage;

public class Game {
    Gui gui;
    public Game(Stage stage){
        Player player = new Player();
        this.gui = new Gui(stage, player);

        (new Thread(new Update(this.gui, player))).start();

    }

}
