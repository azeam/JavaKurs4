package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Gui {
    private GraphicsContext background;
    private ImageView hero;
    private boolean goNorth, goSouth, goEast, goWest;
    private Pane root = new Pane();
    private Player player;
    private ArrayList<Room> roomGroup;
    private Group walls = new Group();
    private boolean gameBeat = false;
    private boolean doorKeyFound = false;
    private Update update;

    private Label playerLabel, otherInvLabel, messageLabel;
    private List<Integer> monsterImageUpdates = new ArrayList<Integer>();
    private List<ImageView> personsList = new ArrayList<ImageView>();
    private List<ImageView> itemsList = new ArrayList<ImageView>();
    private List<GameObject> itemsObjList = new ArrayList<GameObject>();
    private AnimationTimer timer;

    private final Image heroImage1 = new Image(getClass().getResource("/hero1.png").toExternalForm());
    private final Image heroImage2 = new Image(getClass().getResource("/hero2.png").toExternalForm());
    private final Image heroImage3 = new Image(getClass().getResource("/hero3.png").toExternalForm());
    private final Image monsterImage1 = new Image(getClass().getResource("/monster1.png").toExternalForm());
    private final Image monsterItemImage1 = new Image(getClass().getResource("/monster_item1.png").toExternalForm());
    private final Image monsterImage2 = new Image(getClass().getResource("/monster2.png").toExternalForm());
    private final Image monsterItemImage2 = new Image(getClass().getResource("/monster_item2.png").toExternalForm());
    private final Image monsterImage3 = new Image(getClass().getResource("/monster3.png").toExternalForm());
    private final Image monsterItemImage3 = new Image(getClass().getResource("/monster_item3.png").toExternalForm());
    private final Image keyImage = new Image(getClass().getResource("/key.png").toExternalForm());
    private final Image keyMasterImage = new Image(getClass().getResource("/key_master.png").toExternalForm());
    private final Image keyGroundImage = new Image(getClass().getResource("/key_ground.png").toExternalForm());
    private final Image chestImage = new Image(getClass().getResource("/chest.png").toExternalForm());
    private final Image chestOpenImage = new Image(getClass().getResource("/chest_open.png").toExternalForm());
    private final Image doorImage = new Image(getClass().getResource("/door.png").toExternalForm());

    private int changeHeroImg = 0;
    private Stage stage;

    // set up base GUI items
    public Gui(Stage stage, Player player) {
        this.player = player;
        this.stage = stage;
        Canvas canvasBG = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.background = canvasBG.getGraphicsContext2D();
        this.hero = new ImageView(heroImage1);

        Group basement = new Group(hero);

        this.root.setFocusTraversable(true);
        this.root.getChildren().add(canvasBG);
        this.root.getChildren().add(basement);
        this.root.setBackground(Background.EMPTY); // needed for labels to not remove gc bg color
        Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Color.BLACK);

        basement.requestFocus();
        setHeroMovement(basement);
        moveHeroTo(Constants.MARGIN + Constants.ROOM_WIDTH / 2, Constants.MARGIN + Constants.ROOM_HEIGHT / 2);

        stage.setResizable(false);
        stage.setTitle("The Basement");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(scene);
        stage.show();
    }

    public void setUpWallsAndLabels() {
        for (int i = 1; i < this.roomGroup.size(); i++) {
            if (this.roomGroup.get(i).getRoomId() % 2 == 0) {
                paintInnerWalls(this.roomGroup.get(i).getRoomId(), "up");
            } else {
                paintInnerWalls(this.roomGroup.get(i).getRoomId(), "down");
            }
        }
        this.playerLabel = new Label("Inventory of " + Constants.PLAYER_NAME);
        this.playerLabel.setLayoutX(Constants.WINDOW_WIDTH / 2 + Constants.MARGIN);
        this.playerLabel.setLayoutY(Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
        this.playerLabel.setId("playerLabel");
        this.playerLabel.setTextFill(Color.WHITE);
        this.otherInvLabel = new Label();
        this.otherInvLabel.setLayoutX(Constants.MARGIN);
        this.otherInvLabel.setLayoutY(Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
        this.otherInvLabel.setId("otherInvLabel");
        this.otherInvLabel.setTextFill(Color.WHITE);
        this.messageLabel = new Label();
        this.messageLabel.setLayoutX(Constants.WINDOW_WIDTH / 2 - this.messageLabel.getText().length() * 4); // default capital font seems to be about 5 px wide/char
        this.messageLabel.setLayoutY(Constants.MARGIN / 2 - 8);
        this.messageLabel.setId("messageLabel");
        this.messageLabel.setBackground(new Background(new BackgroundFill(Color.PURPLE, new CornerRadii(5.0), new Insets(-5.0))));
        this.messageLabel.setTextFill(Color.WHITE);
        this.root.getChildren().addAll(otherInvLabel, playerLabel, messageLabel);
        paintWalls();
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
        Npc person;
        for (int i = 0; i < personGroup.size(); i++) {
            int monsterInt = monsterImageUpdates.get(i);
            monsterInt = monsterInt + 1;
            monsterImageUpdates.set(i, monsterInt);
            person = personGroup.get(i);
            if (person.isCarrying()) {
                if (monsterInt == 20) {
                    this.personsList.get(i).setImage(monsterItemImage1);
                }
                else if (monsterInt == 40) {
                    this.personsList.get(i).setImage(monsterItemImage2);
                }
                else if (monsterInt == 60) {
                    monsterInt = 0;
                    monsterImageUpdates.set(i, monsterInt);
                    this.personsList.get(i).setImage(monsterItemImage3);
                }
            } else {
                if (monsterInt == 20) {
                    this.personsList.get(i).setImage(monsterImage1);
                }
                else if (monsterInt == 40) {
                    this.personsList.get(i).setImage(monsterImage2);
                }
                else if (monsterInt == 60) {
                    monsterInt = 0;
                    monsterImageUpdates.set(i, monsterInt);
                    this.personsList.get(i).setImage(monsterImage3);
                }
            }
            this.personsList.get(i).setTranslateX(personGroup.get(i).getPosX());
            this.personsList.get(i).setTranslateY(personGroup.get(i).getPosY());
        }
    }

    // clear painted inventory
    public void hideInventory(boolean both) {
        if (both) {
            this.background.clearRect(0, Constants.MARGIN + Constants.ROOM_HEIGHT + 5, Constants.WINDOW_WIDTH - Constants.MARGIN,
            Constants.ROOM_HEIGHT);
            // needs loop because lookup only returns first item, does not work by eg. setting node to class and removing it, different reference
            for (int i = 0; i < Constants.INV_SIZE_PLAYER; i++) {
                this.root.getChildren().remove(this.root.lookup(".rightItem"));
            }
        }
        else {
            this.background.clearRect(0, Constants.MARGIN + Constants.ROOM_HEIGHT + 5, Constants.WINDOW_WIDTH / 2 - Constants.MARGIN,
            Constants.ROOM_HEIGHT);
        }
        this.otherInvLabel.setText("");
        this.root.getChildren().remove(this.root.lookup(".leftItem"));
    }

    public void showInventory(int x, int y, GameObject gameObject, String owner, Object ownerType) {
        
        if (ownerType instanceof Container) {
            Container chest = (Container) ownerType;       
            if (!chest.isOpen()) {
                int id = chest.getId();
                showMessage("Unable to unlock. The chest is engraved with the number " + id);                
                return;
            }
        }
        if (owner != null) {
            this.otherInvLabel.setText("Inventory of " + owner);
        }
        this.background.setStroke(Color.WHITE);
        this.background.strokeRect(x, y, Constants.OBJ_SIZE, Constants.OBJ_SIZE);

        if (gameObject != null) {
            if (gameObject instanceof Key) {
                Key key = (Key) gameObject;
                ImageView itemImg = new ImageView(keyImage);
                if (key.isMaster()) {
                    itemImg = new ImageView(keyMasterImage);
                }
                itemImg.setX(x);
                itemImg.setY(y);
                this.background.fillText(gameObject.toString(), x - 2, y - 5);
                
                itemImg.getStyleClass().clear();
                itemsList.add(itemImg);
                itemsObjList.add(gameObject);
                if (owner == Constants.PLAYER_NAME) {
                    itemImg.setOnMouseClicked(inventoryItemClicked(itemImg, ownerType, gameObject));
                    itemImg.getStyleClass().add("rightItem");
                    itemImg.setStyle("-fx-cursor: hand;");
                } 
                else {
                    itemImg.getStyleClass().add("leftItem");
                }
                itemImg.setUserData(gameObject);
                this.root.getChildren().add(itemImg);
            }
        }
    }

    // if possible to trade, show trade image when selecting player item
    private EventHandler<MouseEvent> inventoryItemClicked(ImageView itemImg, Object ownerType, GameObject gameObject) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // disable trading after finding the door key (and completing the game in case npc is on top of player)     
                if (!gameBeat && !doorKeyFound && Constants.GL_NPC_HIT != null) {
                    for (int i = 0; i < itemsList.size(); i++) {
                        itemsList.get(i).setOpacity(1);                        
                    }               
                    itemImg.setOpacity(0.3);
                
                    player.setSelectedPlayerObject(gameObject);
                    Image exImage = new Image(Constants.EXCHANGE_IMAGE_LOC);
                    ImageView exImgView = new ImageView(exImage);
                    exImgView.setX(Constants.WINDOW_WIDTH / 2 - 20);
                    exImgView.setY(Constants.MARGIN * 3 + Constants.ROOM_HEIGHT - 6);
                    exImgView.setOpacity(0.3);
                    exImgView.setStyle("-fx-cursor: hand;");
                    exImgView.setOnMouseEntered(mouseEvent -> {
                        exImgView.setOpacity(1);
                    });
                    exImgView.setOnMouseExited(mouseEvent -> {
                        exImgView.setOpacity(0.3);
                    });
                    exImgView.getStyleClass().add("leftItem");
                    exImgView.setOnMouseClicked(exchangeItemHandler(itemImg));
                    root.getChildren().add(exImgView);
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
                        if (itemToAddNpc != null && itemToAddPlayer != null) {
                            npcInv.remove(npcInv.getInventory()[0]);
                            player.getInventory().remove(player.getSelectedPlayerObject());
                            npcInv.addToInventory(npcInv, itemToAddNpc);
                            player.getInventory().addToInventory(player.getInventory(), itemToAddPlayer);
    
                            hideInventory(true);
                            setUpInventory(player.getInventory(), player);
                            setUpInventory(person.getInventory(), person);
                            person.setDirection(Direction.getOpposite(person.getDirection()));
                            player.setSelectedPlayerObject(null); // reset selection after trading
                        }
                    }
                };
            }
        };
    }

    private void paintInnerWalls(int order, String doorLocation) {
        Rectangle inner = new Rectangle(Constants.WALL_WIDTH, Constants.WALL_SIZE);
        inner.setX(Constants.MARGIN + Constants.ROOM_WIDTH * order);
        
        if (doorLocation.equals("down")) {
            inner.setY(Constants.MARGIN);
        }
        else {
            inner.setY(Constants.ROOM_HEIGHT - Constants.WALL_SIZE + Constants.MARGIN);
        }
        walls.getChildren().add(inner);
    }

    // paint background and outer walls
    private void paintWalls() {
        Rectangle top = new Rectangle(Constants.ALL_ROOMS_WIDTH, Constants.WALL_WIDTH);
        top.setX(Constants.MARGIN);
        top.setY(Constants.MARGIN);

        Rectangle bottom = new Rectangle(Constants.ALL_ROOMS_WIDTH + Constants.WALL_WIDTH, Constants.WALL_WIDTH);
        bottom.setX(Constants.MARGIN);
        bottom.setY(Constants.MARGIN + Constants.ROOM_HEIGHT);
        
        Rectangle left = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
        left.setX(Constants.MARGIN);
        left.setY(Constants.MARGIN);

        ImageView door = new ImageView(doorImage);
        door.setUserData("door");
        Door doorObject = new Door(null, 0);
        itemsObjList.add(doorObject);
        itemsList.add(door);
        door.setX(Constants.MARGIN - 10);
        door.setY(Constants.ROOM_HEIGHT);
        
        Rectangle right = new Rectangle(Constants.WALL_WIDTH, Constants.ROOM_HEIGHT);
        right.setX(Constants.MARGIN + Constants.ALL_ROOMS_WIDTH);
        right.setY(Constants.MARGIN);
        
        walls.getChildren().addAll(top, bottom, left, right);
        this.background.setFill(Color.BLACK);
        this.background.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.ROOM_HEIGHT + Constants.MARGIN * 2);
        Rectangle nodeWall;
        for (Node wall : walls.getChildren()) {
            nodeWall = (Rectangle) wall;
            this.background.setFill(Color.WHITE);
            this.background.fillRect(nodeWall.getX(), nodeWall.getY(), nodeWall.getWidth(), nodeWall.getHeight());
        }

        Button newGameBtn = new Button("New game");
        newGameBtn.setLayoutX(Constants.MARGIN);
        newGameBtn.setLayoutY(Constants.MARGIN - 43);
        newGameBtn.setStyle("-fx-cursor: hand; -fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), linear-gradient(#20262b, #191d22), radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); -fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-padding: 10 20 10 20;");
        newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                update.musicPlayer("stop", "", false);
                Player player = new Player();
                Gui gui = new Gui(stage, player);
                (new Thread(new Update(gui, player))).start();
            }
        });

        this.root.getChildren().addAll(door, newGameBtn);
    }

    public void setUpPerson(ArrayList<Npc> personGroup) {
        for (int i = 0; i < personGroup.size(); i++) {
            ImageView monster;
            if (personGroup.get(i).isCarrying()) {
                monster = new ImageView(monsterItemImage1);
            }
            else {
                monster = new ImageView(monsterImage1);
            }
            monsterImageUpdates.add(0);
            this.personsList.add(monster);
            this.root.getChildren().add(monster);
        }
    }

    public void setUpItems() {
        for (int i = 0; i < this.roomGroup.size(); i++) {
            for (GameObject g: this.roomGroup.get(i).getInventory().getInventory()) {
                if (g != null) {
                    addItem(g);
                }    
            }
        }
    }
    
    public boolean wallCollision(int nextX, int nextY, int hitboxX, int hitboxY) {
        Rectangle nodeWall, rectPerson;
        Shape intersect;
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
        Rectangle rectPerson = new Rectangle(Constants.NPC_WIDTH, Constants.NPC_HEIGHT);
        rectPerson.setX(newX);
        rectPerson.setY(newY);
        Shape intersect = Shape.intersect(rectPlayer, rectPerson);
        if (intersect.getBoundsInParent().getWidth() > 0) {
            return true;
        }
        return false;
    }

    public boolean npcItemCollision(Npc person, Room room, int nextX, int nextY, int hitboxX, int hitboxY) {
        GameObject object = null;
        int i = 0;
        Rectangle rectPerson;
        for (Node item : this.itemsList) {
            rectPerson = new Rectangle(hitboxX, hitboxY);
            rectPerson.setX(nextX);
            rectPerson.setY(nextY);
            if (item.getBoundsInParent().intersects(rectPerson.getBoundsInParent())) {
                
                // npc item pickup/collision
                object = this.itemsObjList.get(i);
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
        Rectangle rectPerson;
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
            this.root.getChildren().add(itemImg);
        }         
    }

    public void removeObj(GameObject object, Node item) {
        this.itemsList.remove(item);
        this.itemsObjList.remove(object);
    //    System.out.println("removing object " + object.toString() + " item " + item);
        this.root.getChildren().remove(item);        
    }

    // hero movement based on https://gist.github.com/jewelsea/8321740
    private void setHeroMovement(Group basement) {
        basement.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:     goNorth = true; break;
                    case S:     goSouth = true; break;
                    case A:     goWest  = true; break;
                    case D:     goEast  = true; break;
                    default: break;
                }
            }
        });

        basement.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:     goNorth = false; break;
                    case S:     goSouth = false; break;
                    case A:     goWest  = false; break;
                    case D:     goEast  = false; break;
                    default: break;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 3;
                if (goSouth) dy += 3;
                if (goEast)  dx += 3;
                if (goWest)  dx -= 3;
 
                moveHeroBy(dx, dy);
            }
        };
        timer.start();
    }

    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = this.hero.getBoundsInParent().getWidth()  / 2;
        final double cy = this.hero.getBoundsInParent().getHeight() / 2;

        double x = cx + this.hero.getLayoutX() + dx;
        double y = cy + this.hero.getLayoutY() + dy;
        if (!gameBeat) {
            moveHeroTo(x, y);
        }
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
        
        if (!wallCollision(newX, newY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT) && hitObject == null) {
            player.setPosX(newX);
            player.setPosY(newY);

            this.hero.relocate(newX, newY);
            this.hero.toFront(); // TODO: not working as intended
            if (this.update != null) { // is null on init
                this.update.setPaused(false);
            }
            showMessage(""); // hide message
            changeHeroImg++;
            if (changeHeroImg == 4) {
                this.hero.setImage(heroImage2);
            }
            else if (changeHeroImg == 8) {
                this.hero.setImage(heroImage3);
            }
            else if (changeHeroImg == 12) {
                changeHeroImg = 0;
                this.hero.setImage(heroImage1);
            }
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
                update.setPaused(true);
                tryUnlockChest(chest, hitObject, hitNode);    
            }
        }
        else if (hitNode.getUserData() != null && hitNode.getUserData() == "door") {
            for (GameObject playerObject : player.getInventory().getInventory()) {
                if (playerObject instanceof Key) {
                    Key key = (Key) playerObject;
                    if (key.isMaster()) {
                        this.messageLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(5.0), new Insets(-5.0))));
                        this.messageLabel.setLayoutX(Constants.WINDOW_WIDTH / 2 - this.messageLabel.getText().length() * 5);
                        this.messageLabel.setText("CONGRATULATIONS, YOU ESCAPED THE BASEMENT!");
                        this.update.musicPlayer("stop", "/loop.wav", true);
                        this.update.musicPlayer("start", "/win.wav", false);
                        this.timer.stop();
                        this.gameBeat = true;
                    }
                }
            }
        }
        
    }

    private void tryUnlockChest(Container chest, GameObject hitObject, Node hitNode) {
        boolean chestUnlocked = false;
        GameObject objToRemove = null;
        Key key = null;
        // try to unlock with all keys in inventory
        for (GameObject playersObject: player.getInventory().getInventory()) {
            if (playersObject instanceof Key) {
                key = (Key) playersObject;
                if (key.getId() == chest.getId()) {
                    chestUnlocked = true;
                    objToRemove = playersObject;
                    break;
                }
            }
        }

        // if player unlocks chest, remove the key from game
        if (chestUnlocked) {
            chest.setOpen(true);
            ImageView lockedChest = (ImageView) hitNode;
            lockedChest.setImage(chestOpenImage);
            player.getInventory().remove(objToRemove); // remove key from player inv
            if (chest.getInventory().getInventory()[0] != null) {
                GameObject keyToTake = chest.getInventory().getInventory()[0];
                player.getInventory().addToInventory(player.getInventory(), keyToTake); // add master key to player inv
                chest.getInventory().remove(keyToTake); // remove from chest inv
                showMessage("Door key found, head to the exit!");
                this.doorKeyFound = true;
            }
            else {
                showMessage("Opened the chest but nothing was found inside, the key " + key.getId() + " vanished");
            }
            // refresh inventories
            hideInventory(true);
            setUpInventory(player.getInventory(), player);                   
        }
        hideInventory(false);
        setUpInventory(chest.getInventory(), chest); 
    }

	public void setTimer(AnimationTimer timer) {
        this.timer = timer;
	}

	public void showMessage(String string) {
        if (this.messageLabel != null) {
            if (!this.messageLabel.getText().contains("Door key found")) {
                this.messageLabel.setText(string);
            }
        this.messageLabel.setLayoutX(Constants.WINDOW_WIDTH / 2 - this.messageLabel.getText().length() * 4); // default font seems to be about 4 px wide/char
	    }
    }

	public void setUpdateRef(Update update) {
        this.update = update;
	}
}