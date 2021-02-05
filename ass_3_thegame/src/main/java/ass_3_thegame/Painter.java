package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public final class Painter {    
    static Group walls = new Group();

    private Rectangle top, bottom, left, right, inner, nodeWall, rectPerson, nodeItem;
    private List<ImageView> personsList = new ArrayList<ImageView>();
    private List<Rectangle> itemsList = new ArrayList<Rectangle>();
    private List<GameObject> itemsObjList = new ArrayList<GameObject>();

    private Shape intersect;
    private static final Image monsterImage = new Image("https://www.bufonaturvard.se/images/monster2.png");
    private static final Image monsterItemImage = new Image("https://www.bufonaturvard.se/images/monster_item2.png");

    public void paint(Pane root, ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
        for (int i = 0; i < personGroup.size(); i++) {
            Npc person = personGroup.get(i);
            if (person.isCarrying()) {
                personsList.get(i).setImage(monsterItemImage);
            }
            else {
                personsList.get(i).setImage(monsterImage);
            }
            personsList.get(i).setTranslateX(personGroup.get(i).getPosX());
            personsList.get(i).setTranslateY(personGroup.get(i).getPosY());  
                                 
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

    public void setUpPerson(Pane root, ArrayList<Npc> personGroup) {
        for (int i = 0; i < personGroup.size(); i++) {
            ImageView monster = new ImageView(monsterImage);
            personsList.add(monster);
        }
        Platform.runLater(() -> {
            root.getChildren().addAll(personsList);    
        });
    }

    public void setUpItems(Pane root, ArrayList<Room> roomGroup) {
        for (int i = 0; i < roomGroup.size(); i++) {
            for (GameObject g: roomGroup.get(i).getInventory().getInventory()) {
                if (g != null) {
                    addItem(root, g);
                }    
            }
        }
    }

    public boolean wallCollision(int nextX, int nextY, int hitboxX, int hitboxY) {
        for (Node wall : walls.getChildren()) {
            nodeWall = (Rectangle) wall;
            rectPerson = new Rectangle(hitboxX, hitboxY);
            rectPerson.setX(nextX);
            rectPerson.setY(nextY);
            intersect = Shape.intersect(nodeWall, rectPerson);
            if (intersect.getBoundsInParent().getWidth() > 0) {
                return true;
            } 
        }
        return false;
    }

    public boolean itemCollision(Pane root, Npc person, Room room, int nextX, int nextY, int hitboxX, int hitboxY) {
        GameObject object = null;
        int i = 0;
        for (Node item : this.itemsList) {
            nodeItem = (Rectangle) item;
            rectPerson = new Rectangle(hitboxX, hitboxY);
            rectPerson.setX(nextX);
            rectPerson.setY(nextY);

            intersect = Shape.intersect(nodeItem, rectPerson);
            if (intersect.getBoundsInParent().getWidth() > 0) {
                // just check any element for hit manually
                if (root == null && person == null && room == null) {
                    return true;
                }
                // npc item pickup/collision
                object = itemsObjList.get(i);
                if (!object.isPickable()) {
                    person.setDirection(Direction.getOpposite(person.getDirection()));
                    return true;
                }
                else if (!person.isCarrying() && object.isPickable()) {
                    if (room.getInventory().exchangeItem(object, person.getInventory(),
                            "npcPickup", nextX, nextY)) {   
                                itemsList.remove(item);
                                itemsObjList.remove(object);
                                Platform.runLater(() -> {
                                    root.getChildren().remove(item);
                                });  
                                return true;       
                            }
                }                  
            } 
            i++;
        }
        return false;
	}

	public void addItem(Pane root, GameObject g) {
        if (g != null) {
            Rectangle item = new Rectangle(g.getPosX(), g.getPosY(), Constants.OBJ_SIZE, Constants.OBJ_SIZE);
            if (g.getType() == "Key") {
                item.setFill(Color.YELLOW);
            }
            else if (g.getType() == "Chest") {
                item.setFill(Color.GREY);
            }
            else if (g.getType() == "Furniture") {
                item.setFill(Color.WHITE);
            }
            itemsList.add(item);
            itemsObjList.add(g);
            Platform.runLater(() -> {
                root.getChildren().add(item);
            });
        }         
	}
            

}
