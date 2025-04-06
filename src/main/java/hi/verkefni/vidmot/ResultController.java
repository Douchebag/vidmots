package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Leikur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResultController {
    @FXML
    private Button fxNyrLeikurTakki;
    @FXML
    private Button fxHaettaTakki;
    @FXML
    private Label fxResultLabel;

    private Leikur leikur = new Leikur(2);

    @FXML
    public void initialize() {
        Object data = ViewSwitcher.getCurrentData();
        if (data instanceof String) {
            fxResultLabel.setText((String) data);
        } else {
            fxResultLabel.setText("Enginn búinn að vinna");
        }
    }

    /**
     * Atburdarhandler fyrir nyrLeikur takkann
     * @param event atburdur sem kallar á handlerinn
     */
    @FXML
    protected void nyrLeikurHandler(ActionEvent event) {
        ViewSwitcher.switchTo(View.BORD);
        leikur.nyrLeikur();
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
