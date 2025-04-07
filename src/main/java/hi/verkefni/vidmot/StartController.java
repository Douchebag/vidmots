package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Leikur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import hi.verkefni.vidmot.MainController;

public class StartController {
    @FXML
    public Button fxByrjaTakki;
    @FXML
    public TextField fxNafnP1;
    @FXML
    public TextField fxNafnP2;

    MainController mainController;

    @FXML
    protected void ByrjaLeikHandler(ActionEvent event) {
        String p1 = fxNafnP1.getText();
        String p2 = fxNafnP2.getText();

        String[] playerNames = { p1, p2 };
        Leikur leikur = new Leikur(playerNames);

        leikur.getLeikmadur(0).setLeikmadur(p1);
        leikur.getLeikmadur(1).setLeikmadur(p2);
        ViewSwitcher.setLeikur(leikur);

        ViewSwitcher.setData(new String[] { p1, p2 });

        ViewSwitcher.switchTo(View.BORD);
    }
}
