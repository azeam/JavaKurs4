package ass_3_thegame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Painter {

    // TODO: collision detection with nodes
    
    static Group walls = new Group();
    private Rectangle top, bottom, left, right, inner;

    public void paint(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.ROOM_HEIGHT + Constants.MARGIN * 2);
    }

    public void setUpWalls(GraphicsContext gc, int order, String doorLocation) {
        top = new Rectangle(Constants.ALL_ROOMS_WIDTH, Constants.WALL_WIDTH);
        top.setX(Constants.MARGIN);
        top.setY(Constants.MARGIN);

        bottom = new Rectangle(Constants.ALL_ROOMS_WIDTH, Constants.WALL_WIDTH);
        bottom.setX(Constants.MARGIN);
        bottom.setY(Constants.MARGIN + Constants.ROOM_HEIGHT);
        
        left = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
        left.setX(Constants.MARGIN);
        left.setY(Constants.MARGIN);

        right = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
        right.setX(Constants.MARGIN + Constants.ALL_ROOMS_WIDTH);
        right.setY(Constants.MARGIN);

        inner = new Rectangle(Constants.WALL_WIDTH, Constants.WALL_SIZE);
        inner.setX(Constants.ROOM_WIDTH * order);
        if (doorLocation.equals("down")) {
            inner.setY(Constants.MARGIN);
        }
        else {
            inner.setY(Constants.ROOM_HEIGHT - Constants.WALL_SIZE + Constants.MARGIN);
        }

        walls.getChildren().addAll(top, bottom, left, right, inner);
    }

    public void paintRoom(GraphicsContext gc) {
        draw(gc, walls);
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

    private void draw(GraphicsContext gc, Group walls) {
        gc.setFill(Color.WHITE);
        for (Node n : walls.getChildren()) {
            if (n instanceof Rectangle) {
                Rectangle r = (Rectangle) n;
                gc.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            }
        }
    }

}
