package ass_3_thegame;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
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
        Painter painter;
        boolean running, goNorth, goSouth, goEast, goWest;
        Pane root = new Pane();

        public Gui(Stage stage, Painter painter) {
            this.painter = painter;
            Canvas canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            Canvas canvasBG = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            background = canvasBG.getGraphicsContext2D();

            heroImage = new Image(HERO_IMAGE_LOC);
            hero = new ImageView(heroImage);

            Group dungeon = new Group(hero);

            root.setFocusTraversable(true);
            root.getChildren().add(canvasBG);
            root.getChildren().add(canvas);
            root.getChildren().add(dungeon);
            Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Color.GREEN);

            dungeon.requestFocus();
            setHeroMovement(dungeon);
            moveHeroTo(Constants.MARGIN + Constants.ROOM_WIDTH / 2, Constants.MARGIN + Constants.ROOM_HEIGHT / 2);

            stage.setResizable(false);
            stage.setTitle("The Game");
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.setScene(scene);
            stage.show();
        }

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
    
            if (!painter.wallCollision((int) (x - cx), (int) (y - cy), Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT)) {
                    hero.relocate(x - cx, y - cy);
                    hero.toFront(); // TODO: not working, not important after setting up collision and pause for pickup
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
			return this.painter.itemCollision(this.root, person, room, newX, newY, Constants.NPC_WIDTH, Constants.NPC_HEIGHT);
		}
    
    }









