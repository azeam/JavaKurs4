package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/*Extremt enkelt Gui för att kunna komma igång.
Snygga gärna till/gör ett eget. Men tänk på att gör GUI:s INTE är ett kursmoment - så fastna inte här!
 */

    public class Gui {
        private GraphicsContext background;
        private Image heroImage;
        private Node  hero;
        private boolean running, goNorth, goSouth, goEast, goWest;
        private Pane root = new Pane();
        private Player player;
        private ArrayList<Room> roomGroup;
        static Group walls = new Group();

        private Rectangle top, bottom, left, right, inner, nodeWall, rectPerson, nodeItem;
        private List<ImageView> personsList = new ArrayList<ImageView>();
        private List<Rectangle> itemsList = new ArrayList<Rectangle>();
        private List<GameObject> itemsObjList = new ArrayList<GameObject>();
    
        private Shape intersect;
        private static final Image monsterImage = new Image(Constants.MONSTER_IMG_LOC);
        private static final Image monsterItemImage = new Image(Constants.MONSTER_IMG_ITEM_LOC);

        public Gui(Stage stage, Player player) {
            this.player = player;
            Canvas canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            Canvas canvasBG = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            this.background = canvasBG.getGraphicsContext2D();

            heroImage = new Image(Constants.HERO_IMAGE_LOC);
            hero = new ImageView(heroImage);

            Group dungeon = new Group(hero);

            root.setFocusTraversable(true);
            root.getChildren().add(canvasBG);
            root.getChildren().add(canvas);
            root.getChildren().add(dungeon);
            Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Color.BLACK);

            dungeon.requestFocus();
            setHeroMovement(dungeon);
            moveHeroTo(Constants.MARGIN + Constants.ROOM_WIDTH / 2, Constants.MARGIN + Constants.ROOM_HEIGHT / 2);

            stage.setResizable(false);
            stage.setTitle("The Game");
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.setScene(scene);
            stage.show();
        }

		public void setUpWalls(ArrayList<Room> roomGroup) {
            for (int i = 1; i < roomGroup.size(); i++) {
                if (roomGroup.get(i).getRoomId() %2 == 0) {
                    paintWalls(roomGroup.get(i).getRoomId(), "up");
                }
                else {
                    paintWalls(roomGroup.get(i).getRoomId(), "down");
                }
            }
		}

		public void setUpInventory(Inventory inventory, Object owner) {
            if (inventory == null) {
                showInventory(0, 0, null, null, null);   
                return;                 
            }
            int x = owner instanceof Player ? Constants.WINDOW_WIDTH / 2 + Constants.MARGIN : Constants.MARGIN;
            int y = Constants.MARGIN * 3 + Constants.ROOM_HEIGHT;
            
            for (int i = 0; i < inventory.getInventory().length; i++) {       
                if (inventory.getInventory()[i] != null) {
                    showInventory(x + (i * (Constants.OBJ_SIZE + 15)), y, inventory.getInventory()[i], inventory.getOwnerName(), owner);                    
                }
                else {
                    showInventory(x + (i * (Constants.OBJ_SIZE + 15)), y, null, inventory.getOwnerName(), owner);
                }
            }
		}

		public void setRoomGroup(ArrayList<Room> roomGroup) {
            this.roomGroup = roomGroup;
        }
        
        public void paint(ArrayList<Npc> personGroup) {
            for (int i = 0; i < personGroup.size(); i++) {
                Npc person = personGroup.get(i);
                if (person.isCarrying()) {
                    personsList.get(i).setImage(monsterItemImage);
                } else {
                    personsList.get(i).setImage(monsterImage);
                }
                personsList.get(i).setTranslateX(personGroup.get(i).getPosX());
                personsList.get(i).setTranslateY(personGroup.get(i).getPosY());
    
            }
        }

        private void hideOtherInventory() {
            background.clearRect(0, Constants.MARGIN + Constants.ROOM_HEIGHT + 5, Constants.WINDOW_WIDTH / 2 - Constants.MARGIN,
            Constants.ROOM_HEIGHT);
            Platform.runLater(() -> {
                root.getChildren().remove(root.lookup("#npcItem"));
                root.getChildren().remove(root.lookup("#exchangeImage"));
            });
        }

        public void showInventory(int x, int y, GameObject gameObject, String owner, Object ownerType) {
            hideOtherInventory();
            background.setStroke(Color.WHITE);
            background.strokeRect(x, y, Constants.OBJ_SIZE, Constants.OBJ_SIZE);
    
            if (owner == Constants.PLAYER_NAME) {
                background.fillText("Inventory of " + owner, Constants.WINDOW_WIDTH / 2 + Constants.MARGIN,
                        Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
            } else if (owner != null) {
                background.fillText("Inventory of " + owner, Constants.MARGIN, Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
    
            }
            if (gameObject != null) {
                String type = gameObject.getType();
                if (type == "Key") {
                    Image image = new Image(Constants.KEY_IMAGE_LOC);
                    ImageView itemImg = new ImageView(image);
                    itemImg.setX(x);
                    itemImg.setY(y);
                    itemImg.setId("npcItem");
                    if (owner == Constants.PLAYER_NAME) {
                        itemImg.setId("playerItem");
                    }
                    if (ownerType instanceof Npc) {
                        Image exImage = new Image(Constants.EXCHANGE_IMAGE_LOC);
                        ImageView exImgView = new ImageView(exImage);
                        exImgView.setX(Constants.WINDOW_WIDTH / 2);
                        exImgView.setY(Constants.MARGIN * 3 + Constants.ROOM_HEIGHT);
                        exImgView.setId("exchangeImage");
                        exImgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (ownerType instanceof Npc) {
                                    Npc person = (Npc) ownerType;
                                    Inventory npcInv = person.getInventory();
                                    // TODO: fix exchange, "npcPickup" will set the [0] item to be exchanged but item is not removed
                                    // from player inv
                                    if (npcInv.exchangeItem(gameObject, player.getInventory(), "npcPickup", 0, 0)) {
                                        setUpInventory(player.getInventory(), player);
                                        setUpInventory(person.getInventory(), person);
                                    }
                                }
                                // TODO: exchange item
                                // TODO: set hitbox, transparent area does not work
                            }
                        });
                        Platform.runLater(() -> {
                            root.getChildren().add(exImgView); 
                        });
                    }
                    Platform.runLater(() -> {
                        root.getChildren().add(itemImg);    
                    });
                }
               
            }
        }

        public void paintWalls(int order, String doorLocation) {
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
            background.setFill(Color.BLACK);
            background.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.ROOM_HEIGHT + Constants.MARGIN * 2);
            for (Node wall : walls.getChildren()) {
                nodeWall = (Rectangle) wall;
                background.setFill(Color.WHITE);
                background.fillRect(nodeWall.getX(), nodeWall.getY(), nodeWall.getWidth(), nodeWall.getHeight());
            }
        }

        public void setUpPerson(ArrayList<Npc> personGroup) {
            for (int i = 0; i < personGroup.size(); i++) {
                ImageView monster = new ImageView(monsterImage);
                personsList.add(monster);
            }
            Platform.runLater(() -> {
                root.getChildren().addAll(personsList);    
            });
        }

        public void setUpItems(ArrayList<Room> roomGroup) {
            for (int i = 0; i < roomGroup.size(); i++) {
                for (GameObject g: roomGroup.get(i).getInventory().getInventory()) {
                    if (g != null) {
                        addItem(g);
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
    
        public boolean playerNpcCollision(Npc person, int newX, int newY) {
                    Rectangle rectPlayer = new Rectangle(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
                    rectPlayer.setX(player.getPosX());
                    rectPlayer.setY(player.getPosY());              
                    rectPerson = new Rectangle(Constants.NPC_WIDTH, Constants.NPC_HEIGHT);
                    rectPerson.setX(newX);
                    rectPerson.setY(newY);
                    intersect = Shape.intersect(rectPlayer, rectPerson);
                    if (intersect.getBoundsInParent().getWidth() > 0) {
                            return true;
                    }
                    return false;
                }
    
        public boolean npcItemCollision(Npc person, Room room, int nextX, int nextY, int hitboxX, int hitboxY) {
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
                                    removeObj(object, item); 
                                    return true;       
                                }
                    }                  
                } 
                i++;
            }
            return false;
        }
        
        public Object[] getHitItem(int nextX, int nextY, int hitboxX, int hitboxY) {
            Object[] returnObj = new Object[2];
            int i = 0;
            for (Node item : this.itemsList) {
                nodeItem = (Rectangle) item;
                rectPerson = new Rectangle(hitboxX, hitboxY);
                rectPerson.setX(nextX);
                rectPerson.setY(nextY);
    
                intersect = Shape.intersect(nodeItem, rectPerson);
                if (intersect.getBoundsInParent().getWidth() > 0) {
                    returnObj[0] = itemsObjList.get(i);
                    returnObj[1] = item;
                } 
                i++;
            }
            return returnObj;
        }
    
        public void addItem(GameObject g) {
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
    
        public void removeObj(GameObject object, Node item) {
            this.itemsList.remove(item);
            this.itemsObjList.remove(object);
            Platform.runLater(() -> {
                root.getChildren().remove(item);
            });  
        }

        // hero movement based on https://gist.github.com/jewelsea/8321740
        private void setHeroMovement(Group dungeon) {
            dungeon.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:    goNorth = true; break;
                        case DOWN:  goSouth = true; break;
                        case LEFT:  goWest  = true; break;
                        case RIGHT: goEast  = true; break;
                        case SHIFT: running = true; break;
                        default: break;
                    }
                }
            });
    
            dungeon.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:    goNorth = false; break;
                        case DOWN:  goSouth = false; break;
                        case LEFT:  goWest  = false; break;
                        case RIGHT: goEast  = false; break;
                        case SHIFT: running = false; break;
                        default: break;
                    }
                }
            });

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    int dx = 0, dy = 0;
    
                    if (goNorth) dy -= 1;
                    if (goSouth) dy += 1;
                    if (goEast)  dx += 1;
                    if (goWest)  dx -= 1;
                    if (running) { dx *= 3; dy *= 3; }
    
                    moveHeroBy(dx, dy);
                }
            };
            timer.start();
        }

        private void moveHeroBy(int dx, int dy) {
            if (dx == 0 && dy == 0) return;
    
            final double cx = hero.getBoundsInParent().getWidth()  / 2;
            final double cy = hero.getBoundsInParent().getHeight() / 2;
    
            double x = cx + hero.getLayoutX() + dx;
            double y = cy + hero.getLayoutY() + dy;
    
            moveHeroTo(x, y);
        }
    
        private void moveHeroTo(double x, double y) {
            final double cx = hero.getBoundsInParent().getWidth()  / 2;
            final double cy = hero.getBoundsInParent().getHeight() / 2;
    
            int newX = (int) (x - cx);
            int newY = (int) (y - cy);
            Object[] hitItem = getHitItem(newX, newY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
            GameObject hitObject = (GameObject) hitItem[0];
            Node hitNode = (Node) hitItem[1];
            if (!wallCollision(newX, newY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT)
            && hitObject == null) {
                    player.setPosX(newX);
                    player.setPosY(newY);

                    hero.relocate(newX, newY);
                    hero.toFront(); // TODO: not working, not important after setting up collision and pause for pickup
                    Constants.GL_PAUSED = false;
            }
            else if (hitObject != null) {
                if (hitObject.isPickable()) {
                    Constants.GL_PAUSED = true;
                    player.setCurRoom();
                    Room room = roomGroup.get(player.getCurRoom() - 1);

                    if (room.getInventory().exchangeItem(hitObject, player.getInventory(), "playerPickup", newX, newY)) {   
                        removeObj(hitObject, hitNode);        
                        setUpInventory(player.getInventory(), player);
                    }
                    
                }
            }
        }

	
    }