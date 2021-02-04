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
        boolean running, goNorth, goSouth, goEast, goWest;
        Pane root = new Pane();

        public Gui(Stage stage) {
            Canvas canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            Canvas canvasBG = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            background = canvasBG.getGraphicsContext2D();

            heroImage = new Image(HERO_IMAGE_LOC);
            hero = new ImageView(heroImage);

            Group dungeon = new Group(hero);

            moveHeroTo(Constants.WINDOW_WIDTH / 2, Constants.ROOM_HEIGHT / 2);

            root.setFocusTraversable(true);
            root.getChildren().add(canvasBG);
            root.getChildren().add(canvas);
            root.getChildren().add(dungeon);
            Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Color.GREEN);

            dungeon.requestFocus();
            dungeon.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:    goNorth = true; break;
                        case DOWN:  goSouth = true; break;
                        case LEFT:  goWest  = true; break;
                        case RIGHT: goEast  = true; break;
                        case SHIFT: running = true; break;
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

            

            stage.setResizable(false);
            stage.setTitle("The Game");
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.setScene(scene);
            stage.show();
        }

        private void moveHeroBy(int dx, int dy) {
            if (dx == 0 && dy == 0) return;
    
            System.out.println(dx);
            final double cx = hero.getBoundsInParent().getWidth()  / 2;
            final double cy = hero.getBoundsInParent().getHeight() / 2;
    
            double x = cx + hero.getLayoutX() + dx;
            double y = cy + hero.getLayoutY() + dy;
    
            moveHeroTo(x, y);
        }
    
        private void moveHeroTo(double x, double y) {
            final double cx = hero.getBoundsInParent().getWidth()  / 2;
            final double cy = hero.getBoundsInParent().getHeight() / 2;
    
            if (x - cx >= 0 &&
                x + cx <= Constants.WINDOW_WIDTH &&
                y - cy >= 0 &&
                y + cy <= Constants.WINDOW_HEIGHT) {
                    hero.setTranslateX(x - cx);
                    hero.relocate(x - cx, y - cy);
            }
        }

        
        public void setShowObjects(Painter painter, ArrayList<Npc> personGroup, ArrayList<Room> roomGroup) {
                painter.paint(this.root, personGroup, roomGroup);
        }

        public void setUpPerson(Painter painter, ArrayList<Npc> personGroup) {
            painter.setUpPerson(this.root, personGroup);
        }

		public void setUpWalls(Painter painter, ArrayList<Room> roomGroup) {
            for (int i = 1; i < roomGroup.size(); i++) {
                if (roomGroup.get(i).getRoomId() %2 == 0) {
                    painter.setUpWalls(background, roomGroup.get(i).getRoomId(), "up");
                }
                else {
                    painter.setUpWalls(background, roomGroup.get(i).getRoomId(), "down");
                }
            }
		}

        public void addItem(Painter painter, GameObject gameObject) {
            painter.addItem(root, gameObject);
        }
        
		public void setUpItems(Painter painter, ArrayList<Room> roomGroup) {
            painter.setUpItems(this.root, roomGroup);
		}

		public boolean itemCollision(Painter painter, Npc person, Room room, int newX, int newY) {
			return painter.itemCollision(root, person, room, newX, newY);
		}
    
    }









