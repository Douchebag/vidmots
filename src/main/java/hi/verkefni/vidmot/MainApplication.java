package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Búin til sena með einhverjum viðmótshlut
        var scene = new Scene(new Pane());

        // Setjum senuna sem núverandi senu
        stage.setScene(scene);
        ViewSwitcher.setScene(scene);

        // skiptum yfir í viðmótstré fyrir LOGIN
        ViewSwitcher.switchTo(View.BORD);

        // tengjum senuna við gluggann
        stage.setScene(scene);

        // sýnum glugggann
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}