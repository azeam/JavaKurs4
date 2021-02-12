package ass_3_thegame;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TradeClickHandler {
    Gui gui;

    public TradeClickHandler(Gui gui) {
        this.gui = gui;
    }

    // if possible to trade, show trade image when selecting player item
    public EventHandler<MouseEvent> inventoryItemClicked(ImageView itemImg, Object ownerType, GameObject gameObject) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // disable trading after finding the door key (and completing the game in case
                // npc is on top of player)
                if (!gui.gameBeat && !gui.doorKeyFound && gui.hitPerson != null) {
                    for (int i = 0; i < gui.itemsList.size(); i++) {
                        gui.itemsList.get(i).setOpacity(1);
                    }
                    itemImg.setOpacity(0.3);

                    gui.player.setSelectedPlayerObject(gameObject);
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
                    gui.root.getChildren().add(exImgView);
                }
            }

            // get selected item and trade it with npc
            private EventHandler<? super MouseEvent> exchangeItemHandler(ImageView itemImg) {
                return new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Npc person = gui.hitPerson;
                        Inventory npcInv = person.getInventory();
                        GameObject itemToAddPlayer = npcInv.getInventory()[0];
                        GameObject itemToAddNpc = gui.player.getSelectedPlayerObject();
                        if (itemToAddNpc != null && itemToAddPlayer != null) {
                            npcInv.remove(npcInv.getInventory()[0]);
                            gui.player.getInventory().remove(gui.player.getSelectedPlayerObject());
                            npcInv.addToInventory(npcInv, itemToAddNpc);
                            gui.player.getInventory().addToInventory(gui.player.getInventory(), itemToAddPlayer);

                            gui.hideInventory(true);
                            gui.setUpInventory(gui.player.getInventory(), gui.player);
                            gui.setUpInventory(person.getInventory(), person);
                            person.setDirection(Direction.getOpposite(person.getDirection()));
                            gui.player.setSelectedPlayerObject(null); // reset selection after trading
                            //Sound sound = new Sound();
                            gui.sounds.soundPlayer(Constants.SOUND_PICKUP);
                        }
                    }
                };
            }
        };
    }
}
