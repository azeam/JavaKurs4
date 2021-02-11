package ass_3_thegame;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Labels {
    public List<Label> getLabels() {
        List<Label> labels = new ArrayList<Label>();
        
        Label playerLabel = new Label("Inventory of " + Constants.PLAYER_NAME);
        playerLabel.setLayoutX(Constants.WINDOW_WIDTH / 2 + Constants.MARGIN);
        playerLabel.setLayoutY(Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
        playerLabel.setId("playerLabel");
        playerLabel.setTextFill(Color.WHITE);

        Label otherInvLabel = new Label();
        otherInvLabel.setLayoutX(Constants.MARGIN);
        otherInvLabel.setLayoutY(Constants.MARGIN * 2 + Constants.ROOM_HEIGHT);
        otherInvLabel.setId("otherInvLabel");
        otherInvLabel.setTextFill(Color.WHITE);
        
        Label messageLabel = new Label();
        // default font (for me) seems to be about 4 px wide
        messageLabel.setLayoutX(Constants.WINDOW_WIDTH / 2 - messageLabel.getText().length() * 4); 
        messageLabel.setLayoutY(Constants.MARGIN / 2 - 8);
        messageLabel.setId("messageLabel");
        messageLabel.setBackground(new Background(new BackgroundFill(Color.PURPLE, new CornerRadii(5.0), new Insets(-5.0))));
        messageLabel.setTextFill(Color.WHITE);
        
        labels.add(playerLabel);
        labels.add(otherInvLabel);
        labels.add(messageLabel);
        
        return labels;
    }
}
