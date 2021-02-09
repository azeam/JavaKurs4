package ass_3_thegame;

import java.util.ArrayList;
import java.util.Iterator;
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

public class Gui {
    private GraphicsContext background;
    private Image heroImage;
    private Node hero;
    private boolean running, goNorth, goSouth, goEast, goWest;
    private Pane root = new Pane();
    private Player player;
    private ArrayList<Room> roomGroup;
    private Group walls = new Group();

    private Rectangle top, bottom, left, right, inner, nodeWall, rectPerson;
    private List<ImageView> personsList = new ArrayList<ImageView>();
    private List<ImageView> itemsList = new ArrayList<ImageView>();
    private List<GameObject> itemsObjList = new ArrayList<GameObject>();

    private Shape intersect;
    private static final Image monsterImage = new Image(Constants.MONSTER_IMG_LOC);
    private static final Image monsterItemImage = new Image(Constants.MONSTER_IMG_ITEM_LOC);
    private static final Image keyImage = new Image(Constants.KEY_IMAGE_LOC);
    private static final Image keyMasterImage = new Image(Constants.KEY_MASTER_IMAGE_LOC);
    private static final Image keyGroundImage = new Image(Constants.KEY_GROUND_IMAGE_LOC);
    private static final Image chestImage = new Image(Constants.CHEST_IMAGE_LOC);
    private static final Image chestOpenImage = new Image(Constants.CHEST_OPEN_IMAGE_LOC);
    private static final Image doorImage = new Image(Constants.DOOR_IMAGE_LOC);

    // set up base GUI items
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

    public void setUpWalls() {
        for (int i = 1; i < roomGroup.size(); i++) {
            if (roomGroup.get(i).getRoomId() % 2 == 0) {
                paintWalls(roomGroup.get(i).getRoomId(), "up");
            } else {
                paintWalls(roomGroup.get(i).getRoomId(), "down");
            }
        }
    }

    public void setUpInventory(Inventory inventory, Object owner) {
        if (inventory == null) {
            hideInventory(false);
            return;
        }
        // right side for player
        int x = owner instanceof Player ? Constants.WINDOW_WIDTH / 2 + Constants.MARGIN : Constants.MARGIN;
        int y = Constants.MARGIN * 3 + Constants.ROOM_HEIGHT;

        for (int i = 0; i < inventory.getInventory().length; i++) {
            if (inventory.getInventory()[i] != null) {
                showInventory(x + (i * (Constants.OBJ_SIZE + 15)), y, inventory.getInventory()[i],
                        inventory.getOwnerName(), owner);
            } else {
                showInventory(x + (i * (Constants.OBJ_SIZE + 15)), y, null, inventory.getOwnerName(), owner);
            }
        }
    }

    public void setRoomGroup(ArrayList<Room> roomGroup) {
        this.roomGroup = roomGroup;
    }

    // update npc position in GUI
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

    // clear painted inventory
    public void hideInventory(boolean both) {
            if (both) {
                background.clearRect(0, Constants.MARGIN + Constants.ROOM_HEIGHT + 5, Constants.WINDOW_WIDTH - Constants.MARGIN,
                Constants.ROOM_HEIGHT);
                Platform.runLater(() -> {
                    root.getChildren().remove(".playerItem");
                });
            }
            else {
                background.clearRect(0, Constants.MARGIN + Constants.ROOM_HEIGHT + 5, Constants.WINDOW_WIDTH / 2 - Constants.MARGIN,
                Constants.ROOM_HEIGHT);
            }
            Platform.runLater(() -> {
                root.getChildren().remove(root.lookup(".containerItem"));
                root.getChildren().remove(root.lookup(".npcItem"));
                root.getChildren().remove(root.lookup(".exchangeImage"));
            });
        }

        public void showInventory(int x, int y, GameObject gameObject, String owner, Object ownerType) {
            if (owner == Constants.PLAYER_NAME) {
                background.fillText("Inventory of " + owner, Constants.WINDOW_WIDTH / 2 + Constants.MARGIN,
                        Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
            } 
            else if (ownerType instanceof Container) {
                Container chest = (Container) ownerType;       
                if (!chest.isOpen()) {
                    background.fillText("Unable to unlock " + owner, Constants.MARGIN, Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);  
                    return;
                }
                else {
                    background.fillText("Inventory of " + owner, Constants.MARGIN, Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);    
                }
            }
            else if (owner != null) {
                background.fillText("Inventory of " + owner, Constants.MARGIN, Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
            }

            background.setStroke(Color.WHITE);
            background.strokeRect(x, y, Constants.OBJ_SIZE, Constants.OBJ_SIZE);

            if (gameObject != null) {
                if (gameObject instanceof Key) {
                    Key key = (Key) gameObject;
                    ImageView itemImg = new ImageView(keyImage);
                    if (key.isMaster()) {
                        itemImg = new ImageView(keyMasterImage);
                    }
                    itemImg.setX(x);
                    itemImg.setY(y);
                    background.fillText(gameObject.toString(), x, y); // TODO: WTF, changing this does nothing and text remains????
                    
                    itemImg.getStyleClass().clear();
                    if (owner == Constants.PLAYER_NAME) {
                        itemImg.getStyleClass().add("playerItem");
                        itemImg.setOnMouseClicked(inventoryItemClicked(itemImg, ownerType, gameObject));
                    } 
                    else if (ownerType instanceof Npc) {
                        itemImg.getStyleClass().add("npcItem");
                    }
                    else if (ownerType instanceof Container) {
                        itemImg.getStyleClass().add("containerItem");
                    }
                    itemImg.setUserData(gameObject);
                    showObj(itemImg);
                }
            }
        }

        // if possible to trade, show trade image when selecting player item
        private EventHandler<MouseEvent> inventoryItemClicked(ImageView itemImg, Object ownerType, GameObject gameObject) {
            return new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {                    
                    itemImg.setOpacity(0.5); 
                    player.setSelectedPlayerObject(gameObject);
                    System.out.println(itemImg.getStyleClass());
                    if (Constants.GL_NPC_HIT != null) {
                        Image exImage = new Image(Constants.EXCHANGE_IMAGE_LOC);
                        ImageView exImgView = new ImageView(exImage);
                        exImgView.setX(Constants.WINDOW_WIDTH / 2);
                        exImgView.setY(Constants.MARGIN * 3 + Constants.ROOM_HEIGHT);
                        exImgView.getStyleClass().add("exchangeImage");
                        exImgView.setOnMouseClicked(exchangeItemHandler(itemImg));
                        showObj(exImgView);
                        // TODO: disable selection for other keys
                    }
                }

                // get selected item and trade it with npc
                private EventHandler<? super MouseEvent> exchangeItemHandler(ImageView itemImg) {
                    return new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Npc person = Constants.GL_NPC_HIT;
                            Inventory npcInv = person.getInventory();
                            GameObject itemToAddPlayer = npcInv.getInventory()[0];
                            GameObject itemToAddNpc = player.getSelectedPlayerObject();
                            npcInv.remove(npcInv.getInventory()[0]);
                            player.getInventory().remove(player.getSelectedPlayerObject());
                            npcInv.addToInventory(npcInv, itemToAddNpc);
                            player.getInventory().addToInventory(player.getInventory(), itemToAddPlayer);

                            hideInventory(true);
                            setUpInventory(player.getInventory(), player);
                            setUpInventory(person.getInventory(), person);
                            person.setDirection(Direction.getOpposite(person.getDirection()));
                        }
                    };
                }
            };
        }

        // paint background
        public void paintWalls(int order, String doorLocation) {
            top = new Rectangle(Constants.ALL_ROOMS_WIDTH, Constants.WALL_WIDTH);
            top.setX(Constants.MARGIN);
            top.setY(Constants.MARGIN);
    
            bottom = new Rectangle(Constants.ALL_ROOMS_WIDTH + Constants.WALL_WIDTH, Constants.WALL_WIDTH);
            bottom.setX(Constants.MARGIN);
            bottom.setY(Constants.MARGIN + Constants.ROOM_HEIGHT);
           
            left = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
            left.setX(Constants.MARGIN);
            left.setY(Constants.MARGIN);

            ImageView door = new ImageView(doorImage);
            door.setUserData("door");
            Door doorObject = new Door(null, 0);
            itemsObjList.add(doorObject);
            itemsList.add(door);
            door.setX(Constants.MARGIN - 10);
            door.setY(Constants.ROOM_HEIGHT);
           
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
            showObj(door);
        }

        public void setUpPerson(ArrayList<Npc> personGroup) {
            for (int i = 0; i < personGroup.size(); i++) {
                ImageView monster = new ImageView(monsterImage);
                personsList.add(monster);
                showObj(monster);
            }
        }

        public void setUpItems() {
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
                rectPerson = new Rectangle(hitboxX, hitboxY);
                rectPerson.setX(nextX);
                rectPerson.setY(nextY);
                if (item.getBoundsInParent().intersects(rectPerson.getBoundsInParent())) {
                    
                    // npc item pickup/collision
                    object = itemsObjList.get(i);
                    if (!object.isPickable()) {
                        person.setDirection(Direction.getOpposite(person.getDirection()));
                        return true;
                    }
                    else if (!person.isCarrying() && object.isPickable()) {
                        if (room.getInventory().exchangeItem(object, person.getInventory(),
                                "npcPickup", nextX, nextY)) {  
                                    removeObj(object, item); // remove drawn but picked up item from GUI
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
                rectPerson = new Rectangle(hitboxX, hitboxY);
                rectPerson.setX(nextX);
                rectPerson.setY(nextY);
    
                if (item.getBoundsInParent().intersects(rectPerson.getBoundsInParent())) {
                    returnObj[0] = itemsObjList.get(i);
                    returnObj[1] = item;
                } 
                i++;
            }
            return returnObj;
        }
    
        public void addItem(GameObject g) {
            if (g != null) {
                ImageView itemImg = null;
                if (g.getType() == "Key") {
                    itemImg = new ImageView(keyGroundImage);
                }
                else if (g.getType() == "Chest") {
                    Container chest = (Container) g;
                    System.out.println(chest.getPosX() + " " + chest.getPosY() + chest.isOpen());
                    if (chest.isOpen()) {
                        itemImg = new ImageView(chestOpenImage);
                    }
                    else {
                        itemImg = new ImageView(chestImage);
                    }
                }
                itemImg.setX(g.getPosX());
                itemImg.setY(g.getPosY());
                this.itemsList.add(itemImg);
                this.itemsObjList.add(g);
                showObj(itemImg);
            }         
        }
    
        private void showObj(Node node) {
            Platform.runLater(() -> {
                root.getChildren().add(node);
            });
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
                        case W:     goNorth = true; break;
                        case S:     goSouth = true; break;
                        case A:     goWest  = true; break;
                        case D:     goEast  = true; break;
                        case SHIFT: running = true; break;
                        default: break;
                    }
                }
            });
    
            dungeon.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case W:     goNorth = false; break;
                        case S:     goSouth = false; break;
                        case A:     goWest  = false; break;
                        case D:     goEast  = false; break;
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
            player.setCurRoom();
            player.setSelectedPlayerObject(null);
            
            if (!wallCollision(newX, newY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT)
            && hitObject == null) {
                    player.setPosX(newX);
                    player.setPosY(newY);

                    hero.relocate(newX, newY);
                    hero.toFront(); // TODO: not working
                    Constants.GL_PAUSED = false;
            }
            else if (hitObject != null) {
                handleHitObject(hitObject, hitNode, newX, newY);
            }
        }

        // player collision with chest/key/door
        private void handleHitObject(GameObject hitObject, Node hitNode, int newX, int newY) {
            Container chest = null;

            if (hitObject.isPickable()) {
                Room room = roomGroup.get(player.getCurRoom() - 1);
                if (room.getInventory().exchangeItem(hitObject, player.getInventory(), "playerPickup", newX, newY)) {   
                    removeObj(hitObject, hitNode);  
                    hideInventory(true);      
                    setUpInventory(player.getInventory(), player);
                }
            }
            else if (hitObject.getType() == "Chest") {
                chest = (Container) hitObject;
                if (!chest.isOpen()) {
                    Constants.GL_PAUSED = true;
                    boolean chestUnlocked = false;
                    GameObject objToRemove = null;
                    // try to unlock with all keys in inventory
                    for (GameObject playersObject: player.getInventory().getInventory()) {
                        if (playersObject instanceof Key) {
                            Key key = (Key) playersObject;
                            if (key.getId() == chest.getId()) {
                                chestUnlocked = true;
                                objToRemove = playersObject;
                                break;
                            }
                        }
                    }

                    // if player unlocks chest, take the key
                    if (chestUnlocked) {
                        chest.setOpen(true);
                        addItem(chest); // add open chest img
                        removeObj(hitObject, hitNode); // remove locked chest img
                        player.getInventory().remove(objToRemove); // remove key from player inv
                        if (chest.getInventory().getInventory()[0] != null) {
                            System.out.println("Chest contains key, taking it");
                            GameObject keyToTake = chest.getInventory().getInventory()[0];
                            player.getInventory().addToInventory(player.getInventory(), keyToTake); // add master key to player inv
                            chest.getInventory().remove(keyToTake); // remove from chest inv
                            this.background.fillText("MASTER KEY FOUND", Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 200);
                        }     
                        
                    }
                    
                }
                 // refresh inventory
                 // TODO: chest needs to be set higher up or removed key will remain in place when opening 
                 // an empty chest for some reason, but then "can't open" won't show, fix this
                hideInventory(true);
                setUpInventory(chest.getInventory(), chest); 
                setUpInventory(player.getInventory(), player);                 
            }
            else if (hitNode.getUserData() != null && hitNode.getUserData() == "door") {
                for (GameObject playerObject : player.getInventory().getInventory()) {
                    if (playerObject instanceof Key) {
                        Key key = (Key) playerObject;
                        if (key.isMaster()) {
                            this.background.fillText("YOU BEAT GAME!", Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 100);

                        }
                    }
                }
            }
            
        }

	
    }