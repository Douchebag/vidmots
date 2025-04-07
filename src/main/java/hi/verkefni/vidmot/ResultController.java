package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Leikmadur;
import hi.verkefni.vinnsla.Leikur;
import hi.verkefni.vinnsla.ResultData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

public class ResultController {
    @FXML
    private Button fxNyrLeikurTakki;
    @FXML
    private Button fxHaettaTakki;
    @FXML
    private Label fxResultLabel;

    private String[] playerNames;

    @FXML
    public void initialize() {
        Object data = ViewSwitcher.getCurrentData();
        if (data instanceof ResultData) {
            ResultData resultData = (ResultData) data;
            fxResultLabel.setText(resultData.getResultMessage());
            playerNames = resultData.getPlayerNames();
        } else {
            fxResultLabel.setText("Enginn búinn að vinna");
            playerNames = new String[]{"Player1", "Player2"};
        }
    }

    /**
     * Atburdarhandler fyrir nyrLeikur takkann
     * @param event atburdur sem kallar á handlerinn
     */
    @FXML
    protected void nyrLeikurHandler(ActionEvent event) {
        ViewSwitcher.switchTo(View.BORD, playerNames);
    }

    /**
     * Atburdarhandler fyrir haetta takkann
     * @param actionEvent
     */
    @FXML
    public void HaettaHandler(ActionEvent actionEvent) {
        Platform.exit();
    }
}
