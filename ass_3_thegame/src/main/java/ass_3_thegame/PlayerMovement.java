package ass_3_thegame;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

public class PlayerMovement {
    Gui gui;

	public PlayerMovement(Gui gui) {
        this.gui = gui;
	}

    // hero movement based on https://gist.github.com/jewelsea/8321740
    protected void setHeroMovement(Group basement) {
        basement.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        gui.goNorth = true;
                        break;
                    case S:
                        gui.goSouth = true;
                        break;
                    case A:
                        gui.goWest = true;
                        break;
                    case D:
                        gui.goEast = true;
                        break;
                    default:
                        break;
                }
            }
        });

        basement.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        gui.goNorth = false;
                        break;
                    case S:
                        gui.goSouth = false;
                        break;
                    case A:
                        gui.goWest = false;
                        break;
                    case D:
                        gui.goEast = false;
                        break;
                    default:
                        break;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (gui.goNorth) {
                    dy -= 3;
                }
                if (gui.goSouth) {
                    dy += 3;
                }
                if (gui.goEast) {
                    dx += 3;
                }
                if (gui.goWest) {
                    dx -= 3;
                }

                gui.moveHeroBy(dx, dy);
            }
        };
        timer.start();
    }

}
