package ass_3_thegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {

    public void paint(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.ROOM_HEIGHT + Constants.MARGIN * 2);
    }

    public void paintRoom(GraphicsContext gc, int order, String doorLocation) {
        gc.setFill(Color.WHITE);
        gc.fillRect(Constants.MARGIN, Constants.MARGIN, Constants.ALL_ROOMS_WIDTH, 1); // top
        gc.fillRect(Constants.MARGIN, Constants.MARGIN + Constants.ROOM_HEIGHT, Constants.ALL_ROOMS_WIDTH, 1); // bottom
        gc.fillRect(Constants.MARGIN, Constants.MARGIN, 1, Constants.ROOM_HEIGHT); // left
        gc.fillRect(Constants.MARGIN + Constants.ALL_ROOMS_WIDTH, Constants.MARGIN, 1, Constants.ROOM_HEIGHT); // right
        if (doorLocation.equals("down")) {
            gc.fillRect(Constants.ROOM_WIDTH * order, Constants.MARGIN, 1, Constants.WALL_SIZE);
        }
        else {
            gc.fillRect(Constants.ROOM_WIDTH * order, Constants.ROOM_HEIGHT - Constants.WALL_SIZE + Constants.MARGIN, 1, Constants.WALL_SIZE);
        }
    }

    public void paintPerson(GraphicsContext gc, int x, int y, String name, boolean isCarrying) {
        if (isCarrying) {
            gc.setFill(Color.GREEN);    
        }
        else {
            gc.setFill(Color.RED);
        }
        gc.fillRect(x, y, Constants.NPC_SIZE, Constants.NPC_SIZE);
        gc.fillText(name, x, y);
    }

}
