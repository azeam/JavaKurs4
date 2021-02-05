package ass_3_thegame;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    Painter painter = new Painter();
    new Game(stage, painter);    
  }
}
