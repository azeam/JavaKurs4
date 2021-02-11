package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Rectangle;

public class Rectangles {

    public List<Rectangle> getWalls() {
        List<Rectangle> result = new ArrayList<Rectangle>();
        
        Rectangle top = new Rectangle(Constants.ALL_ROOMS_WIDTH, Constants.WALL_WIDTH);
        top.setX(Constants.MARGIN);
        top.setY(Constants.MARGIN);

        Rectangle bottom = new Rectangle(Constants.ALL_ROOMS_WIDTH + Constants.WALL_WIDTH, Constants.WALL_WIDTH);
        bottom.setX(Constants.MARGIN);
        bottom.setY(Constants.MARGIN + Constants.ROOM_HEIGHT);

        Rectangle left = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
        left.setX(Constants.MARGIN);
        left.setY(Constants.MARGIN);

        Rectangle right = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
        right.setX(Constants.MARGIN + Constants.ALL_ROOMS_WIDTH);
        right.setY(Constants.MARGIN);

        result.add(top);
        result.add(bottom);
        result.add(left);
        result.add(right);
        
        return result;
    }

}
