package hi.verkefni.vinnsla;

public class ResultData {
    private final String resultMessage;
    private final String[] playerNames;

    public ResultData(String resultMessage, String[] playerNames) {
        this.resultMessage = resultMessage;
        this.playerNames = playerNames;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }
}
