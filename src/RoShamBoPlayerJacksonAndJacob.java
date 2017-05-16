import java.util.ArrayList;
import java.util.List;

public class RoShamBoPlayerJacksonAndJacob extends RoShamBoPlayer {


    int lossRun = 0;
    int winRun = 0;
    int tieCount = 0;
    int wins = 0, losses = 0, ties = 0;
    double winLossRatio = .5;

    public RoShamBoPlayerJacksonAndJacob(String player) {
        super(player);
    }

    @Override
    public String makeMove() {
        calcWinLoss();
        List<String> myMoves = getMyMoves();
        List<String> theirMoves = getOpponentMoves();
        if (getMyMoves().size() != 0) {
            didIWin(myMoves.get(getMyMoves().size() - 1), theirMoves.get(getOpponentMoves().size() - 1));
        }
        if (getMyMoves().size() < 5)
            return makeRandomMove();
        if (getMyMoves().size() < 10)
            return lastMoveMode();

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
    final private int didIWin(String myMove, String theirMove) {
        int outcome = -100;
        if (myMove.equals(theirMove))
            outcome = 0;
        else if (myMove.equals("rock") && theirMove.equals("scissors"))
            outcome = 1;
        else if (myMove.equals("scissors") && theirMove.equals("paper"))
            outcome = 1;
        else if (myMove.equals("paper") && theirMove.equals("rock"))
            outcome = 1;
        else {
            outcome = -1;
        }
        if (outcome == -1) {
            lossRun++;
            winRun = 0;
        } else if (outcome == 1) {
            lossRun = 0;
            winRun++;
        } else {
            winRun = 0;
            lossRun = 0;
            tieCount++;
        }
        return outcome;
    }

    final private List<String> getSubList(int start, int length, List<String> list) {
        List<String> tmp = new ArrayList<>();
        if (start > 0 && length + start > list.size()) {
            return tmp;
        }
        return tmp;
    }

}
