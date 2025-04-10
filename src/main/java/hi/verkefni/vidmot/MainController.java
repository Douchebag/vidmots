package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Item;
import hi.verkefni.vinnsla.Leikmadur;
import hi.verkefni.vinnsla.Leikur;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Stýriklasi fyrir Snáka og Stiga leikinn.
 * Sér um viðmótsuppfærslur.
 */
public class MainController {
    @FXML
    private Label fxSkilabod1;
    @FXML
    private Label fxSkilabod2;
    @FXML
    private GridPane fxLeikjabord;
    @FXML
    private ImageView fxTeningur;
    @FXML
    private ImageView player1Piece;
    @FXML
    private ImageView player2Piece;

    private List<Node> reitir;
    private Leikur leikur;
    private String player1Nafn;
    private String player2Nafn;

    public Leikur getLeikur() {
        return leikur;
    }

    /**
     * Upphafsstillir leikbordid og setur upp bindingar
     */
    public void initialize() {
        Object data = ViewSwitcher.getCurrentData();
        if (data instanceof String[]) {
            String[] playerNames = (String[]) data;
            player1Nafn = playerNames[0];
            player2Nafn = playerNames[1];
            leikur = new Leikur(playerNames);
        } else {
            System.out.println("No player names provided, initializing with default names.");
            String[] defaultNames = new String[]{"Player1", "Player2"};
            leikur = new Leikur(defaultNames);
        }

        reitir = fxLeikjabord.getChildren();

        fxTeningur.imageProperty().bind(Bindings.createObjectBinding(() ->
                        new Image(getClass().getResource("/hi/verkefni/vidmot/myndir/"
                                + leikur.kastaNidurstadaProperty().get() + ".jpg").toExternalForm()),
                leikur.kastaNidurstadaProperty()
        ));
        fxSkilabod1.textProperty().bind(leikur.skilabod1Property());
        fxSkilabod2.textProperty().bind(leikur.skilabod2Property());

        addPlayerPiecesToGrid();

        populateBoardWithItems();

        leikur.getLeikmadurReiturProperty(0).addListener((obs, oldValue, newValue) -> {
            movePlayerPiece(player1Piece, newValue.intValue());
        });

        leikur.getLeikmadurReiturProperty(1).addListener((obs, oldValue, newValue) -> {
            movePlayerPiece(player2Piece, newValue.intValue());
        });
    }

    /**
     * bætir við item myndum á tile í GridPane.
     */
    private void populateBoardWithItems() {
        for (Node node : fxLeikjabord.getChildren()) {
            if (node instanceof Pane) {
                Pane tilePane = (Pane) node;
                if (!tilePane.getChildren().isEmpty() && tilePane.getChildren().get(0) instanceof Label) {
                    Label tileLabel = (Label) tilePane.getChildren().get(0);
                    try {
                        int tileNumber = Integer.parseInt(tileLabel.getText());

                        while (tilePane.getChildren().size() > 1) {
                            tilePane.getChildren().remove(1);
                        }

                        Item item = leikur.getBord().getItemAtTileForDisplay(tileNumber);
                        if (item != null) {
                            ImageView itemIcon = new ImageView(new Image(getClass().getResource(
                                    "/hi/verkefni/vidmot/myndir/" + item.getName().replaceAll("\\s+", "") + ".png").toExternalForm()));
                            itemIcon.setFitHeight(40);
                            itemIcon.setFitWidth(40);
                            tilePane.getChildren().add(itemIcon);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
    }

    /**
     * Setur leikmennina á bordid
     */
    private void addPlayerPiecesToGrid() {
        player1Piece = new ImageView(new Image(getClass().getResource("/hi/verkefni/vidmot/myndir/player1.png").toExternalForm()));
        player2Piece = new ImageView(new Image(getClass().getResource("/hi/verkefni/vidmot/myndir/player2.png").toExternalForm()));

        player1Piece.setFitHeight(100);
        player1Piece.setFitWidth(60);
        player2Piece.setFitHeight(100);
        player2Piece.setFitWidth(60);

        movePlayerPiece(player1Piece, 1);
        movePlayerPiece(player2Piece, 1);

        fxLeikjabord.getChildren().addAll(player1Piece, player2Piece);
    }

    /**
     * Faerir leikmann á nýjann reit á bordinu
     * @param playerPiece mynd leikmannsins sem á að færa
     * @param newPosition nýji reiturinn sem leikmadurinn verdur faerdur á
     */
    private void movePlayerPiece(ImageView playerPiece, int newPosition) {
        if (newPosition > 24) {
            newPosition = 24;
        }

        int row = (24 - newPosition) / 6;
        int col = (newPosition - 1) % 6;

        // fyrir zig-zag
        if (row % 2 == 0) {
            col = 5 - col;
        }

        final int finalRow = row;
        final int finalCol = col;

        Platform.runLater(() -> {
            GridPane.setRowIndex(playerPiece, finalRow);
            GridPane.setColumnIndex(playerPiece, finalCol);
        });
    }

    /**
     * Atburdarhandler fyrir teninga takkann
     * kallar líka á populateBoardWithItems til ad eyða myndunum af items sem voru pickud upp
     * @param event atburdur sem kallar á handlerinn
     */
    @FXML
    protected void teningurHandler(ActionEvent event) {
        leikur.leikaLeik();
        Platform.runLater(() -> populateBoardWithItems());
    }

    public void setPlayerNames(String p1, String p2) {
        if (leikur != null) {
            leikur.getLeikmadur(0).setLeikmadur(p1);
            leikur.getLeikmadur(1).setLeikmadur(p2);
        } else {
            System.err.println("Leikur is not initialized. Cannot set player names.");
        }
    }

    public void setLeikur(Leikur leikur) {
        this.leikur = leikur;
    }
}