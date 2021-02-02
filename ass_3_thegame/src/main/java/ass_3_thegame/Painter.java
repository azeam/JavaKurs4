package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Painter {

    // TODO: collision detection with nodes
    
    static Group walls = new Group();
    static Group persons = new Group();
    private Rectangle top, bottom, left, right, inner, nodePerson, nodeWall;
    private List<Rectangle> npc = new ArrayList<Rectangle>();
    private Shape intersect;

    public void paint(GraphicsContext gc, ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        for (int i = 0; i < personGroup.size(); i++) {
            npc.get(i).setX(personGroup.get(i).getPosX());
            npc.get(i).setY(personGroup.get(i).getPosY());
            draw(gc, personGroup, roomGroup);                         
        }
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
        inner.setX(Constants.MARGIN + Constants.ROOM_WIDTH * order);
       
        if (doorLocation.equals("down")) {
            inner.setY(Constants.MARGIN);
        }
        else {
            inner.setY(Constants.ROOM_HEIGHT - Constants.WALL_SIZE + Constants.MARGIN);
        }
        
        walls.getChildren().addAll(top, bottom, left, right, inner);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.ROOM_HEIGHT + Constants.MARGIN * 2);
        for (Node wall : walls.getChildren()) {
            nodeWall = (Rectangle) wall;
            gc.setFill(Color.WHITE);
            gc.fillRect(nodeWall.getX(), nodeWall.getY(), nodeWall.getWidth(), nodeWall.getHeight());
        }
    }

    public void setUpPerson(GraphicsContext gc, ArrayList<Npc> personGroup) {
        for (int i = 0; i < personGroup.size(); i++) {
            npc.add(new Rectangle(Constants.NPC_SIZE, Constants.NPC_SIZE));
        }
        persons.getChildren().addAll(npc);        
    }

    public boolean wallCollision(int nextX, int nextY) {
        for (Node wall : walls.getChildren()) {
            nodeWall = (Rectangle) wall;
            nodePerson = new Rectangle(Constants.NPC_SIZE, Constants.NPC_SIZE);
            nodePerson.setX(nextX);
            nodePerson.setY(nextY);
            intersect = Shape.intersect(nodeWall, nodePerson);
            if (intersect.getBoundsInParent().getWidth() > 0) {
                System.out.println("collision");
                return true;
            } 
        }
        return false;
    }

    public GameObject itemCollision(Room room, int nextX, int nextY) {
        
            for (GameObject object : room.getInventory().getInventory()) {
                if (object != null) {
                    Rectangle nodeObj = new Rectangle(Constants.NPC_SIZE, Constants.NPC_SIZE);
                    nodeObj.setX(object.getPosX());
                    nodeObj.setY(object.getPosY());
                    nodePerson = new Rectangle(Constants.NPC_SIZE, Constants.NPC_SIZE);
                    nodePerson.setX(nextX);
                    nodePerson.setY(nextY);
                    intersect = Shape.intersect(nodeObj, nodePerson);
                    if (intersect.getBoundsInParent().getWidth() > 0) {
                        return object;
                    } 
                }
            }
        
        return null;
	}

    private void draw(GraphicsContext gc, ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        gc.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        for (int i = 0; i < persons.getChildren().size(); i++ ) {
            nodePerson = (Rectangle) persons.getChildren().get(i);
            if (personGroup.get(i).isCarrying()) {
                gc.setFill(Color.GREEN);    
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText(personGroup.get(i).npcName(), personGroup.get(i).getPosX(), personGroup.get(i).getPosY());                        
            gc.fillRect(nodePerson.getX(), nodePerson.getY(), nodePerson.getWidth(), nodePerson.getHeight());
        }

        gc.setFill(Color.YELLOW);    
        for (int i = 0; i < roomGroup.size(); i++ ) {
            GameObject[] roomObjects = roomGroup.get(i).getInventory().getInventory();
            for (GameObject obj: roomObjects) {
                if (obj != null) {
                    gc.fillOval(obj.getPosX(), obj.getPosY(), Constants.NPC_SIZE, Constants.NPC_SIZE);
                }
            }
        } 
            
    }

}
