import java.util.List;

/**
 * Matthew's RPS bot.
 * <p>
 * Designed to play somewhat like a human, against an opponent that plays like one.
 * Checks the opponent's previous moves based on what their last move was, and determine weights for all of their next possible moves.
 * From there,
 */


public class RoShamBoPlayerMatthew extends RoShamBoPlayer {


    public RoShamBoPlayerMatthew(String name) {
        super(name);
    }

    @Override
    public String makeMove() {
        //your code here

        // Checks against exploits

        if (this.getOpponentMoves().size() != this.getMyMoves().size())
            return this.getRandomMove();

        if (this.getOpponentMoves().size() > 1 && this.checkForStreaks(7, this.getOpponentMoves())) {  // Run off against rockbot

            return this.getCounter(this.lastOppMove());

        }


        double[][] weights = this.getPreviousWeights();

        // If we don't have enough data
        if (weights == null) {

            return this.getRandomMove();

        } else {  // Let's use the weights of their last move to determine what they're likely to do

            String move = this.chooseMoveFromWeights(weights);
            //System.out.println(move);
            return move;

        }

    }

    // Sort the numbers
    private double[] sort(double[] arr) {

        // We can be lazy since we're only sorting three numbers
        boolean sorted = false;
        boolean sortedOnIter = false;
        double temp;

        while (!sorted) {

            for (int i = 1; i < arr.length; i++) {

                if (arr[i] < arr[i + 1]) {  // Sort them in REVERSE order, with the higher ones coming first
                    temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    sortedOnIter = true;
                }
            }
            if (!sortedOnIter) {
                sorted = true;
            }

            sortedOnIter = false;

        }

        return arr;
    }

    private boolean freqsWithinTolerance(double tolerance, double target, double[] arr) {

        for (double num : arr) {

            if (Math.abs(num - target) > tolerance)
                return false;

        }

        return true;

    }

    private String chooseMoveFromWeights(double[][] weights) {

        // Get weights of how the opponent typically reacted to the last state
        // TODO: Consider expanding the state size that we search for (similar to how NYT bot works
        // Also, get some weights based on the amount that the opponent used a certain move, to see if they have a
        // tendency to favor some moves over others.

        boolean checkOwnFrequency = true;

        // The weights of the opponent typcally throws out after my last move
        double[] nextMoveWeights = weights[this.moveToEnum(this.getOpponentMoves().get(this.getOpponentMoves().size() - 1))];
        double[] oppFreqWeights = this.weightsFromFrequency(this.getOpponentMoves());
        double[] selfFreqWeights = this.weightsFromFrequency(this.getMyMoves());

        if (this.getOpponentMoves().size() > 50 && this.freqsWithinTolerance(.05, .33, oppFreqWeights)) {  // Opponent is only throwing out random moves, so let's counter it
           // System.out.println("Randombot detected, throwing a random move");
            return this.getRandomMove();

        }

        // TODO: Check against own frequency

        // Already checked against streaks, let's make sure we beat frequency

        if (checkOwnFrequency) {
            for (int i = 0; i < selfFreqWeights.length; i++) {
                if (selfFreqWeights[i] > 0.8) {
                    return this.getLoss(moveFromEnum(i));  // pick the move that would lose against our move
                }
            }
        }


        // Average the weights from frequency and trends

        double[] avgWeights = new double[3];

        for (int i = 0; i < avgWeights.length; i++) {

            avgWeights[i] = (nextMoveWeights[i] + oppFreqWeights[i]) / 2;

        }

        double rand = Math.random();

        // Counter frequency bot play

        // Let's counter possible streaks by occasionally forcing a tie (at 5% chance)
        if (Math.random() < 0.01) {
            int largestWeightIndex = 0;
            double curLargestWeight = 0;

            for (int i = 0; i < selfFreqWeights.length; i++) {

                if (selfFreqWeights[i] > curLargestWeight) {
                    largestWeightIndex = i;
                    curLargestWeight = selfFreqWeights[i];
                }
            }

            return this.moveFromEnum(largestWeightIndex);

        }

        // Otherwise

        for (int i = 0; i < avgWeights.length; i++) {

            if (rand < avgWeights[i]) {

                return this.getCounter(this.moveFromEnum(i));
            }
        }

        // If no weights end up working out, just use a random move.
        return getRandomMove();


    }

    public String getLoss(String move) {

        if (move.equals("rock"))
            return "scissors";
        else if (move.equals("paper"))
            return "rock";
        else
            return "paper";

    }

    public String getRandomMove() {

        int rand = (int) (Math.random() * 3);
        //System.out.println(rand);
        return this.moveFromEnum(rand);
    }

    /**
     * Get all weights of moves based on what they typically throw out after which move.
     * Uses a 2D array representing their last move; rows are rock, paper, scissors, and columns are the weights of the opponent's chances of
     * throwing out r, p, s.
     */
    private double[][] getPreviousWeights() {

        List<String> myMoves = this.getMyMoves();
        List<String> oppMoves = this.getOpponentMoves();

        int[][] moveCount = new int[3][3];

        double[][] weights = new double[3][3];

        if (myMoves.size() < 3)
            return null;

        String[] moves = {"rock", "paper", "scissors"};

        for (int i = 0; i < moveCount.length; i++) {  // rock, paper, scissors

            for (int j = 0; j < myMoves.size() - 1; j++) {  // Scan over the existing move db

                if (myMoves.get(j).equals(moves[i])) {  // If we find a move that matches the current one we're scanning for with moves[i]
                    moveCount[i][moveToEnum(oppMoves.get(j + 1))]++;  // Add what move tends to come after
                }


            }

        }

        // Now, let's clean up the numbers by making them percentages.

        double total = 0.0;


        for (int i = 0; i < moveCount.length; i++) {
            for (int j = 0; j < moveCount[i].length; j++) {
                total += moveCount[i][j];
            }

            for (int k = 0; k < moveCount[i].length; k++) {

                if (moveCount[i][k] == 0)
                    continue;

                weights[i][k] = moveCount[i][k] / total;  // Get its percentage of the total

            }

            total = 0.0;
        }

        return weights;

    }

    /**
     * Calculate weights for the next likely move based on existing patterns (adapted from earlier code in order to be
     * similar to nyt bot).
     * <p>
     * Worth noting that this will likely work well against a human player who intuitively uses patterns without realizing
     * it, but will likely break down when faced with a "robot" that naturally just doesn't.
     *
     * @param stateSize
     * @return
     */
    private double[][] getPreviousHumanWeights(int stateSize) {

        List<String> myMoves = this.getMyMoves();
        List<String> oppMoves = this.getOpponentMoves();

        int[][] moveCount = new int[3][3];

        double[][] weights = new double[3][3];

        if (myMoves.size() < 3)
            return null;

        String[] pastState = new String[stateSize];

        boolean matches = true;

        System.arraycopy(oppMoves.toArray(new String[oppMoves.size()]), oppMoves.size() - 1 - stateSize, pastState, 0, stateSize);

        // Build the past state
        //TODO

        String[] moves = {"rock", "paper", "scissors"};

        for (int i = 0; i < moveCount.length; i++) {  // rock, paper, scissors

            for (int j = stateSize; j < oppMoves.size() - 1; j++) {  // Scan over the existing move db

                // Take the last state and scan the array to see if it has happened before
                for (int k = stateSize; k >= 0; k--) {
                    if (!oppMoves.get(k).equals(pastState[k])) {
                        matches = false;
                        break;
                    }

                }

                if (!matches) {
                    moveCount[i][moveToEnum(oppMoves.get(j + 1))]++;
                    matches = true;
                }


            }

        }

        // Now, let's clean up the numbers by making them percentages.

        double total = 0.0;


        for (int i = 0; i < moveCount.length; i++) {
            for (int j = 0; j < moveCount[i].length; j++) {
                total += moveCount[i][j];
            }

            for (int k = 0; k < moveCount[i].length; k++) {

                if (moveCount[i][k] == 0)
                    continue;

                weights[i][k] = moveCount[i][k] / total;  // Get its percentage of the total

            }

            total = 0.0;
        }

        return weights;

    }

    /**
     * For counter-play against frequency
     */

    //TODO: Counter-play against people using the freq strategy
    private double[] weightsFromFrequency(List<String> moves) {
        double rocks = 0, scissors = 0, paper = 0;
        double[] weights = new double[3];

        for (String move : moves) {
            if (move.equals("rock"))
                rocks++;
            else if (move.equals("scissors"))
                scissors++;
            else
                paper++;
        }

        weights[0] = rocks / moves.size();
        weights[1] = paper / moves.size();
        weights[2] = scissors / moves.size();

        return weights;
    }


    private int moveToEnum(String move) {

        if (move.equals("rock"))
            return 0;
        else if (move.equals("paper"))
            return 1;
        else
            return 2;
    }

    private String moveFromEnum(int index) {

        if (index == 0)
            return "rock";
        else if (index == 1)
            return "paper";
        else
            return "scissors";
    }

    private String getCounter(String move) {

        if (move.equals("rock"))
            return "paper";
        else if (move.equals("paper"))
            return "scissors";
        else
            return "rock";
    }

    private String lastOppMove() {
        return this.getOpponentMoves().get(this.getOpponentMoves().size() - 1);
    }

    private String lastOwnMove() {

        return this.getOpponentMoves().get(this.getMyMoves().size() - 1);
    }

    private boolean checkForStreaks(int length, List<String> moves) {

        if (moves.size() < length)
            return false;

        for (int i = moves.size() - length; i < moves.size() - 1; i++) {

            //System.out.println(moves.size() + " " + i);
            if (!(moves.get(i).equals(moves.get(i + 1))))
                return true;

        }

        return false;

    }

}