package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Leikur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Handles switching between different views in the application.
 */
public class ViewSwitcher {

    private static Scene scene;
    private static Object currentData;
    private static Leikur leikur;

    public static void setLeikur(Leikur l) {
        leikur = l;
    }

    public static Leikur getLeikur() {
        return leikur;
    }

    /**
     * Setur núverandi senu í ViewSwitcher sem scene - enginn breyting á glugga
     *
     * @param scene senan
     */
    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    /**
     * Skipta yfir í viðmótstré sem er lýst í view
     *
     * @param view nafn á .fxml skrá
     */
    public static void switchTo(View view) {
        if (scene == null) {
            System.out.println("No scene was set");
        }

        try {
            System.out.println("Loading from FXML: " + view.getFileName());

            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource("/hi/verkefni/vidmot/" + view.getFileName()));
            Parent root = loader.load();

            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            if (view == View.FIGHT) {
                stage.setHeight(470);
                stage.setWidth(800);
            } else if (view == View.BORD) {
                stage.setHeight(690);
                stage.setWidth(600);
            } else if (view == View.RESULT) {
                stage.setHeight(260);
                stage.setWidth(370);
            }
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Geymir gögn til að nota þau í mismunandi "views"
     */
    public static void setData(Object data) {
        currentData = data;
    }

    /**
     * skilar gögnunum sem við geymum milli sena
     * @return gögn
     */
    public static Object getCurrentData() {
        return currentData;
    }

    /**
     * uppfaerir gögnin sem við erum að geyma og kallar á switchTo
     * aðferðina til að skipta um senu
     * @param view
     * @param newData
     */
    public static void switchTo(View view, Object newData) {
        currentData = newData;
        switchTo(view);
    }
}