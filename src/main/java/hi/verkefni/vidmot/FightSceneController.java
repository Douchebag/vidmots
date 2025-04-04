package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Item;
import hi.verkefni.vinnsla.Leikmadur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class FightSceneController {

    @FXML
    private Pane p1ItemPane1;
    @FXML
    private Pane p1ItemPane2;

    @FXML
    private Pane p2ItemPane1;
    @FXML
    private Pane p2ItemPane2;

    @FXML
    private Label p1StrengthLabel;
    @FXML
    private Label p1DefenceLabel;

    @FXML
    private Label p2StrengthLabel;
    @FXML
    private Label p2DefenceLabel;

    @FXML
    private TextArea fightLogTextArea;

    @FXML
    public void initialize() {
        Object data = ViewSwitcher.getCurrentData();
        if (data instanceof List) {
            List<Leikmadur> players = (List<Leikmadur>) data;
            if (players.size() >= 2) {
                Leikmadur player1 = players.get(0);
                Leikmadur player2 = players.get(1);
                //System.out.println("leikmadur 1 besta vopn: " + player1.getBestaVopn());
                //System.out.println("leikmadur 2 besta vopn: " + player2.getBestaVopn());

                if (player1.getBestaVopn() != null) {
                    ImageView weaponImageView = createImageViewForItem(player1.getBestaVopn());
                    weaponImageView.getStyleClass().add("item-image-view");
                    p1ItemPane1.getChildren().add(weaponImageView);

                }
                if (player1.getBestaBrynja() != null) {
                    ImageView armourImageView = createImageViewForItem(player1.getBestaBrynja());
                    p1ItemPane2.getChildren().add(armourImageView);
                    armourImageView.getStyleClass().add("item-image-view");
                }
                p1StrengthLabel.setText("Strength: " + (player1.getBestaVopn() != null ? player1.getBestaVopn().getBonus() : 0));
                p1DefenceLabel.setText("Defence: " + (player1.getBestaBrynja() != null ? player1.getBestaBrynja().getBonus() : 0));

                if (player2.getBestaVopn() != null) {
                    ImageView weaponImageView = createImageViewForItem(player2.getBestaVopn());
                    weaponImageView.getStyleClass().add("item-image-view");
                    p2ItemPane1.getChildren().add(weaponImageView);
                }
                if (player2.getBestaBrynja() != null) {
                    ImageView armourImageView = createImageViewForItem(player2.getBestaBrynja());
                    armourImageView.getStyleClass().add("item-image-view");
                    p2ItemPane2.getChildren().add(armourImageView);
                }

                p2StrengthLabel.setText("Strength: " + (player2.getBestaVopn() != null ? player2.getBestaVopn().getBonus() : 0));
                p2DefenceLabel.setText("Defence: " + (player2.getBestaBrynja() != null ? player2.getBestaBrynja().getBonus() : 0));
            }
        }
    }

    /**
     * Býr til ImageView object fyrir item-id með því að loada mynd frá resource folderinu
     *
     * @param item itemid sem vid erum ad setja inn mynd af
     * @return ImageView object með mynd af iteminu
     */
    private ImageView createImageViewForItem(Item item) {
        String imagePath = getImagePathForItem(item);
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Finnur pathid ad myndunum
     *
     * @param item itemid sem vid erum ad leita af
     * @return pathid ad myndinni
     */
    private String getImagePathForItem(Item item) {
        String itemName = item.getName().toLowerCase().replaceAll("\\s+", "");

        return "/hi/verkefni/vidmot/myndir/" + itemName + ".png";
    }
}
