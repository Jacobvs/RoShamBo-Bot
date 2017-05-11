import java.util.List;

public class RoShamBoPlayerJacksonAndJacob extends RoShamBoPlayer {


    int lossRun = 0;
    int wins = 0, losses = 0, ties = 0;
    double winLossRatio = .5;

    public RoShamBoPlayerJacksonAndJacob(String player) {
        super(player);
    }

    @Override
    public String makeMove() {
        int tmp = 0;
        calcWinLoss();
        if (getMyMoves().size() != 0)
            tmp = didIWin(getMyMoves().get(getMyMoves().size() - 1), getOpponentMoves().get(getOpponentMoves().size() - 1));
        if (tmp == -1)
            lossRun++;
        else if (tmp == 1)
            lossRun = 0;
        if (getMyMoves().size() < 25)
            return makeRandomMove();
        if (getMyMoves().size() < 50)
            return lastMoveMode();

        // Will change later
        return "rock";
    }

    private String lastMoveMode() {
        int size = getOpponentMoves().size();
        if (size == 0)
            return "rock";
        else
            return getOpponentMoves().get(size - 1);
    }

    private String makeRandomMove() {
        int rand = (int) (Math.random() * 3);
        if (rand == 0)
            return "paper";
        else if (rand == 1)
            return "scissors";
        else
            return "rock";
    }

    private void calcWinLoss() {
        wins = 0;
        losses = 0;
        ties = 0;
        List<String> opponentMoves = getOpponentMoves();
        List<String> myMoves = getMyMoves();
        for (int i = 0; i < opponentMoves.size(); i++) {
            int tmp = didIWin(myMoves.get(i), opponentMoves.get(i));
            if (tmp == 1)
                wins++;
            else if (tmp == -1)
                losses++;
            else
                ties++;
        }

        winLossRatio = wins / losses;
    }

    // Return 1 for win 0 for tie -1 for loss
    private int didIWin(String myMove, String theirMove) {
        if (myMove.equals(theirMove))
            return 0;

        if (myMove.equals("rock") && theirMove.equals("scissors"))
            return 1;
        if (myMove.equals("scissors") && theirMove.equals("paper"))
            return 1;
        if (myMove.equals("paper") && theirMove.equals("rock"))
            return 1;
        return -1;
    }


}
