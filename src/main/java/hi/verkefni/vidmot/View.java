package hi.verkefni.vidmot;

public enum View {
    BORD("bord-view.fxml"),
    FIGHT("fight-scene.fxml"),
    START("start-view.fxml"),
    RESULT("result-view.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
