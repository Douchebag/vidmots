package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Item;
import hi.verkefni.vinnsla.Leikmadur;
import hi.verkefni.vinnsla.ResultData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Label fxPlayer1Nafn;
    @FXML
    private Label fxPlayer2Nafn;


    @FXML
    private TextFlow fightLogTextFlow;
    @FXML
    private ScrollPane fightLogScrollPane;

    private Leikmadur player1;
    private Leikmadur player2;
    private int player1HP = 99;
    private int player2HP = 99;


    @FXML
    public void initialize() {
        fightLogTextFlow.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            fightLogScrollPane.setVvalue(1.0);
        });

        Object data = ViewSwitcher.getCurrentData();
        if (data instanceof List) {
            List<Leikmadur> players = (List<Leikmadur>) data;
            if (players.size() >= 2) {
                player1 = players.get(0);
                fxPlayer1Nafn.setText(player1.getLeikmadur());

                player2 = players.get(1);
                fxPlayer2Nafn.setText(player2.getLeikmadur());

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
        startFight();
    }

    private void startFight() {
        boolean firstAttackerIsP1 = Math.random() < 0.5;

        final Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.5), event -> {
            int damageToP1 = 0;
            int damageToP2 = 0;

            if (firstAttackerIsP1) {
                damageToP2 = calculateDamage(player1, player2);
                player2HP -= damageToP2;
                damageToP1 = calculateDamage(player2, player1);
                player1HP -= damageToP1;
            } else {
                damageToP1 = calculateDamage(player2, player1);
                player1HP -= damageToP1;
                damageToP2 = calculateDamage(player1, player2);
                player2HP -= damageToP2;
            }

            if (firstAttackerIsP1) {
                Color color1 = (damageToP2 > 0) ? Color.RED : Color.BLUE;
                Color color2 = (damageToP1 > 0) ? Color.RED : Color.BLUE;

                appendLogLine(
                        player1.getLeikmadur() + " hits " + player2.getLeikmadur() + " for " + damageToP2 + " damage. (",
                        player2.getLeikmadur() + " HP: ",
                        player2HP,
                        ")",
                        color1
                );
                appendLogLine(
                        player2.getLeikmadur() + " hits " + player1.getLeikmadur() + " for " + damageToP1 + " damage. (",
                        player1.getLeikmadur() + " HP: ",
                        player1HP,
                        ")",
                        color2
                );
                fightLogTextFlow.getChildren().add(new Text("\n"));
            } else {
                Color color1 = (damageToP1 > 0) ? Color.RED : Color.BLUE;
                Color color2 = (damageToP2 > 0) ? Color.RED : Color.BLUE;

                appendLogLine(
                        player2.getLeikmadur() + " hits " + player1.getLeikmadur() + " for " + damageToP1 + " damage. (",
                        player1.getLeikmadur() + " HP: ",
                        player1HP,
                        ")",
                        color1
                );
                appendLogLine(
                        player1.getLeikmadur() + " hits " + player2.getLeikmadur() + " for " + damageToP2 + " damage. (",
                        player2.getLeikmadur() + " HP: ",
                        player2HP,
                        ")",
                        color2
                );
                fightLogTextFlow.getChildren().add(new Text("\n"));
            }

            if (player1HP <= 0 || player2HP <= 0) {
                String resultMessage;
                if (player1HP <= 0 && player2HP <= 0) {
                    resultMessage = ((firstAttackerIsP1 ? "Player 1" : "Player 2") + " vann!\n");
                } else if (player1HP <= 0) {
                    resultMessage = (player2.getLeikmadur() + " vann!\n");
                } else {
                    resultMessage = (player1.getLeikmadur() + " vann!\n");
                }
                Text resultText = new Text(resultMessage + "\n");
                resultText.setFill(Color.WHITE);
                fightLogTextFlow.getChildren().add(resultText);
                timeline.stop();

                String[] playerNames = new String[]{
                        player1.getLeikmadur(),
                        player2.getLeikmadur()
                };

                ResultData resultData = new ResultData(resultMessage, playerNames);
                ViewSwitcher.switchTo(View.RESULT, resultData);
            }

        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void appendLogLine(String prefix, String hpText, int hpValue, String suffix, Color messageColor) {
        Text t1 = new Text(prefix);
        t1.setFill(messageColor);
        Text t2 = new Text(hpText + hpValue);
        t2.setFill(Color.GREEN);
        Text t3 = new Text(suffix + "\n");
        t3.setFill(messageColor);
        fightLogTextFlow.getChildren().addAll(t1, t2, t3);
    }

    private int calculateDamage(Leikmadur attacker, Leikmadur defender) {
        int attackBonus = (attacker.getBestaVopn() != null ? attacker.getBestaVopn().getBonus() : 5);
        int defenseBonus = (defender.getBestaBrynja() != null ? defender.getBestaBrynja().getBonus() : 5);

        double hitChance = (double) attackBonus / (attackBonus + defenseBonus + 10);

        if (Math.random() < hitChance) {
            int maxHit = (int) (attackBonus * 0.2) + 1;
            return (int) (Math.random() * maxHit) + 1;
        } else {
            return 0;
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
