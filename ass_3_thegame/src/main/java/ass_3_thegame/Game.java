package ass_3_thegame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
    Gui gui;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Player player = new Player();
        this.gui = new Gui(stage, player);
        (new Thread(new Update(this.gui, player))).start();
    }

}
