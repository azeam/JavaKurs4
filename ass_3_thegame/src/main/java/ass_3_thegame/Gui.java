package ass_3_thegame;

import java.util.ArrayList;
import java.util.Arrays;

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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*Extremt enkelt Gui för att kunna komma igång.
Snygga gärna till/gör ett eget. Men tänk på att gör GUI:s INTE är ett kursmoment - så fastna inte här!
 */

    public class Gui {
        private GraphicsContext background;
        private static final String HERO_IMAGE_LOC =
            "https://www.bufonaturvard.se/images/hero.png";
        private Image heroImage;
        private Node  hero;
        private Painter painter;
        private boolean running, goNorth, goSouth, goEast, goWest;
        private Pane root = new Pane();
        private Player player;
        private ArrayList<Room> roomGroup;

        public Gui(Stage stage, Painter painter, Player player) {
            this.painter = painter;
            this.player = player;
            Canvas canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            Canvas canvasBG = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            this.background = canvasBG.getGraphicsContext2D();

            heroImage = new Image(HERO_IMAGE_LOC);
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
            Object[] hitItem = painter.getHitItem(newX, newY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
            GameObject hitObject = (GameObject) hitItem[0];
            Node hitNode = (Node) hitItem[1];
            if (!painter.wallCollision(newX, newY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT)
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
                        painter.removeObj(this.root, hitObject, hitNode);        
                        setUpInventory(player.getInventory(), true);
                    }
                    
                }
            }
            else {
                Constants.GL_PAUSED = false;
            }
        }

        
        public void setShowObjects(ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
                this.painter.paint(this.root, personGroup, roomGroup);
        }

        public void setUpPerson(ArrayList<Npc> personGroup) {
            this.painter.setUpPerson(this.root, personGroup);
        }

		public void setUpWalls(ArrayList<Room> roomGroup) {
            for (int i = 1; i < roomGroup.size(); i++) {
                if (roomGroup.get(i).getRoomId() %2 == 0) {
                    this.painter.setUpWalls(background, roomGroup.get(i).getRoomId(), "up");
                }
                else {
                    this.painter.setUpWalls(background, roomGroup.get(i).getRoomId(), "down");
                }
            }
		}

        public void addItem(GameObject gameObject) {
            this.painter.addItem(this.root, gameObject);
        }
        
		public void setUpItems(ArrayList<Room> roomGroup) {
            this.painter.setUpItems(this.root, roomGroup);
		}

		public boolean itemCollision(Npc person, Room room, int newX, int newY) {
			return this.painter.npcItemCollision(this.root, person, room, newX, newY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT);
		}

		public boolean wallCollision(int newX, int newY, int npcWidth, int npcHeight) {
			return this.painter.wallCollision(newX, newY, npcWidth, npcHeight);
		}

		public boolean playerCollision(Npc person, Room room, int newX, int newY) {
            return this.painter.playerNpcCollision(this.player, person, newX, newY);
		}

		public void setUpInventory(Inventory inventory, boolean isPlayer) {
            System.out.println("setup " + Arrays.asList(inventory));
            if (inventory == null) {
                painter.showInventory(this.root, this.background, 0, 0, null, null);   
                return;                 
            }
            int x = isPlayer ? Constants.WINDOW_WIDTH / 2 + Constants.MARGIN : Constants.MARGIN;
            int y = Constants.MARGIN * 3 + Constants.ROOM_HEIGHT;
            
            for (int i = 0; i < inventory.getInventory().length; i++) {       
                if (inventory.getInventory()[i] != null) {
                    painter.showInventory(this.root, this.background, x + (i * (Constants.OBJ_SIZE + 15)), y, inventory.getInventory()[i], inventory.getOwnerName());                    
                }
                else {
                    painter.showInventory(this.root, this.background, x + (i * (Constants.OBJ_SIZE + 15)), y, null, inventory.getOwnerName());
                }
            }
		}

		public void setRoomGroup(ArrayList<Room> roomGroup) {
            this.roomGroup = roomGroup;
		}

	
    }









