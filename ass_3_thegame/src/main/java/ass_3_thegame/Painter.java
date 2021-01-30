package ass_3_thegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {

    public void paint(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants.ALL_ROOMS_WIDTH, Constants.ROOM_HEIGHT);
    }

    public void paintRoom(GraphicsContext gc, int order, String doorLocation) {
        gc.setFill(Color.WHITE);
        if (doorLocation.equals("down")) {
            gc.fillRect(Constants.ROOM_WIDTH * order, 0, 1, Constants.WALL_SIZE);
        }
        else {
            gc.fillRect(Constants.ROOM_WIDTH * order, Constants.ROOM_HEIGHT - Constants.WALL_SIZE, 1, Constants.WALL_SIZE);
        }
    }

    public void paintPerson(GraphicsContext gc, int x, int y, String name) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x, y, Constants.NPC_SIZE, Constants.NPC_SIZE);
        gc.fillText(name, x, y);
    }

}
