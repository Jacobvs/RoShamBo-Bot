import java.util.ArrayList;
import java.util.List;

class RoShamBoPlayer {
    private List<String> myMoves;
    private List<String> opponentMoves;
    private String name;

    public RoShamBoPlayer(String player) {
        name = player;
        myMoves = new ArrayList<String>();
        opponentMoves = new ArrayList<String>();

    }

    public void reset() {
        myMoves = new ArrayList<String>();
        opponentMoves = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void addMyMove(String move) {
        myMoves.add(move);
    }

    public void addOpponentMove(String move) {
        opponentMoves.add(move);
    }

    public List<String> getMyMoves() {
        // security fix: prevents a player from accessing opponent's
        // current move if player goes second in the round
        int size = Math.min(myMoves.size(), opponentMoves.size());
        List<String> moves = new ArrayList<String>(myMoves);
        if (moves.size() == size + 1)
            moves.remove(moves.size() - 1);
        return moves;
    }

    public List<String> getOpponentMoves() {
        // security fix: prevents a player from accessing opponent's
        // current move if player goes second in the round
        int size = Math.min(myMoves.size(), opponentMoves.size());
        List<String> moves = new ArrayList<String>(opponentMoves);
        if (moves.size() == size + 1)
            moves.remove(moves.size() - 1);
        return moves;

    }

    public String makeMove() {
        // your code here
        // You can create new helper methods or instance variables,but you 
        // can not alter/delete any of the methods/variables currently implemented.

        // Just return rock, no matter what.
        int rand = (int) (Math.random() * 3);
        if (rand == 0)
            return "rock";
        else if (rand == 1)
            return "paper";
        else
            return "scissors";
    }


}