package ass_3_thegame;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/*Extremt enkelt Gui för att kunna komma igång.
Snygga gärna till/gör ett eget. Men tänk på att gör GUI:s INTE är ett kursmoment - så fastna inte här!
 */


    public class Gui {
        private GraphicsContext context;
        Painter painter = new Painter();

        public Gui(Stage stage) {
            StackPane root = new StackPane();
            Canvas canvas = new Canvas(800, 600);
            context = canvas.getGraphicsContext2D();

            canvas.setFocusTraversable(true);
            canvas.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case UP:
                        System.out.println("up");
                        break;
                    case DOWN:
                        System.out.println("down");
                        break;
                    case LEFT:
                        System.out.println("left");
                        break;
                    case RIGHT:
                        System.out.println("right");
                        break;
                }
            });

            root.getChildren().add(canvas);

            Scene scene = new Scene(root);


            stage.setResizable(false);
            stage.setTitle("The Game");
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.setScene(scene);
            stage.show();
        }

        
        public void setShowPersons(ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
            painter.paint(context);
            for (Npc person: personGroup) {
                painter.paintPerson(context, person.getPosX(), person.getPosY(), person.npcName());
            }
            for (int i = 1; i < roomGroup.size(); i++) {
                if (roomGroup.get(i).roomId %2 == 0) {
                    painter.paintRoom(context, roomGroup.get(i).roomId, "up");
                }
                else {
                    painter.paintRoom(context, roomGroup.get(i).roomId, "down");
                }
            }
        }

   
    }









