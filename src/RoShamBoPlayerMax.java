import java.util.List;

public class RoShamBoPlayerMax extends RoShamBoPlayer {
    private int moveN;


    public RoShamBoPlayerMax(String name) {
        super(name);


    }

    @Override
    public String makeMove() {
        int k = 1;
        int l = 1;
        int[] myRocks = new int[79];
        int[] myPaper = new int[138];
        int[] myScissors = new int[84];
        int mrocks = 0, mscissors = 0, mpaper = 0;
        int morocks = -1, moscissors = -1, mopaper = -1;
        int urocks = 0, uscissors = 0, upaper = 0;
        List<String> mmoves = getMyMoves();
        List<String> umoves = getOpponentMoves();
        if (umoves.size() == 0)
            moveN = 0;
        for (int i = 0; i < 79; i++) {
            myRocks[i] = k;
            k += l;
            l++;
            if (l > 25)
                l = 2;
        }
        k = 1;
        l = 1;
        for (int i = 0; i < 84; i++) {
            myScissors[i] = k;
            k += l;
            l += l;
            if (l > 32)
                l = 2;
        }
        k = 1;
        l = 2;
        for (int i = 0; i < 138; i++) {
            myPaper[i] = k;
            k += l;
            l = l * l;
            if (l > 32)
                l = 2;
        }

        for (String move : mmoves) {
            if (move.equals("rock")) {
                mrocks++;
                morocks++;
            } else if (move.equals("scissors")) {
                mscissors++;
                moscissors++;
            } else {
                mpaper++;
                mopaper++;
            }
        }
        if (umoves.size() > 3) {
            if (umoves.get(umoves.size() - 1).equals(umoves.get(umoves.size() - 2)) && umoves.get(umoves.size() - 2).equals(umoves.get(umoves.size() - 3)) && umoves.get(umoves.size() - 3).equals(umoves.get(umoves.size() - 4)) && umoves.get(umoves.size() - 1).equals("rock")) {
                moveN++;
                return "paper";
            }
            if (umoves.get(umoves.size() - 1).equals(umoves.get(umoves.size() - 2)) && umoves.get(umoves.size() - 2).equals(umoves.get(umoves.size() - 3)) && umoves.get(umoves.size() - 3).equals(umoves.get(umoves.size() - 4)) && umoves.get(umoves.size() - 1).equals("scissors")) {
                moveN++;
                return "rock";
            }
            if (umoves.get(umoves.size() - 1).equals(umoves.get(umoves.size() - 2)) && umoves.get(umoves.size() - 2).equals(umoves.get(umoves.size() - 3)) && umoves.get(umoves.size() - 3).equals(umoves.get(umoves.size() - 4)) && umoves.get(umoves.size() - 1).equals("paper")) {
                moveN++;
                return "scissors";
            }
        }
        int diff = Math.max(Math.abs(mrocks - mpaper), Math.max(Math.abs(mpaper - mscissors), Math.abs(mscissors - mrocks)));
        if (diff > 3) {
            if (morocks >= moscissors && morocks >= mopaper && umoves.get(umoves.size() - 1).equals("paper"))
                if (mrocks >= mscissors && mrocks >= mpaper) {
                    moveN++;
                    return "scissors";
                }
            if (moscissors >= mopaper && moscissors >= morocks && umoves.get(umoves.size() - 1).equals("rock"))
                if (mscissors >= mpaper && mscissors >= mrocks) {
                    moveN++;
                    return "paper";
                }
            if (mopaper >= moscissors && mopaper >= morocks && umoves.get(umoves.size() - 1).equals("rock"))
                if (mpaper >= mscissors && mpaper >= mrocks) {
                    moveN++;
                    return "rock";
                }
        }
        if (umoves.size() > 2) {
            if (umoves.get(umoves.size() - 1).equals(mmoves.get(mmoves.size() - 2))) {
                if (mmoves.get(mmoves.size() - 1).equals("rock")) {
                    moveN++;
                    return "paper";
                }
                if (mmoves.get(mmoves.size() - 1).equals("paper")) {
                    moveN++;
                    return "scissors";
                }
                if (mmoves.get(mmoves.size() - 1).equals("scissors")) {
                    moveN++;
                    return "rock";
                }
            }
        }
        if (umoves.size() > 1) {
            if (morocks >= moscissors && morocks >= mopaper && umoves.get(umoves.size() - 1).equals("paper"))
                if (mrocks >= mscissors && mrocks >= mpaper) {
                    moveN++;
                    return "scissors";
                }
            if (moscissors >= mopaper && moscissors >= morocks && umoves.get(umoves.size() - 1).equals("rock"))
                if (mscissors >= mpaper && mscissors >= mrocks) {
                    moveN++;
                    return "paper";
                }
            if (mopaper >= moscissors && mopaper >= morocks && umoves.get(umoves.size() - 1).equals("rock"))
                if (mpaper >= mscissors && mpaper >= mrocks) {
                    moveN++;
                    return "rock";
                }
        }
        String a = new String();
        String b = new String();
        String c = new String();
        if (getMyMoves().size() < 6) {
            return "paper";
        }
        if (getMyMoves().size() > 5) {
            a = getOpponentMoves().get(getOpponentMoves().size() - 1);
            b = getOpponentMoves().get(getOpponentMoves().size() - 2);
            c = getOpponentMoves().get(getOpponentMoves().size() - 3);
            if (a == getMyMoves().get(getMyMoves().size() - 2) && b == getMyMoves().get(getMyMoves().size() - 3) &&
                    c == getMyMoves().get(getMyMoves().size() - 4) && getMyMoves().get(getMyMoves().size() - 1) == "rock") {
                return "rock";
            }
            if (a == getMyMoves().get(getMyMoves().size() - 2) && b == getMyMoves().get(getMyMoves().size() - 3) &&
                    c == getMyMoves().get(getMyMoves().size() - 4) && getMyMoves().get(getMyMoves().size() - 1) == "paper") {
                return "paper";
            }
            if (a == getMyMoves().get(getMyMoves().size() - 2) && b == getMyMoves().get(getMyMoves().size() - 3) &&
                    c == getMyMoves().get(getMyMoves().size() - 4) && getMyMoves().get(getMyMoves().size() - 1) == "scissors") {
                return "scissors";
            }
            if (a == c && a == b && a == "rock")
                return "rock";
            if (a == c && a == b && a == "paper")
                return "paper";
            if (a == c && a == b && a == "scissors")
                return "scissors";
            if (a == "rock" && b == "scissors" && c == "paper")
                return "paper";
            if (a == "rock" && b == "paper" && c == "scissors")
                return "scissors";
            if (a == "paper" && b == "scissors" && c == "rock")
                return "rock";
            if (a == "paper" && b == "rock" && c == "scissors")
                return "scissors";
            if (a == "scissors" && b == "rock" && c == "paper")
                return "paper";
            if (a == "scissors" && b == "paper" && c == "rock")
                return "rock";


        }


        if (findNumber(myRocks, moveN)) {
            moveN++;
            return "rock";
        }
        if (findNumber(myScissors, moveN)) {
            moveN++;
            return "scissors";
        }
        if (findNumber(myPaper, moveN)) {
            moveN++;
            return "paper";
        }


        return getOpponentMoves().get(getOpponentMoves().size() - 1);
        
          
       /*int urocks = 0, uscissors = 0, upaper = 0;
        
       /* List<String> moves = getOpponentMoves();
        for (String move : moves)
        {   
            if (move.equals("rock")) 
               urocks++;
            else if (move.equals("scissors"))
               uscissors++;
            else
                upaper++;
        }
        */

    }

    public boolean findNumber(int[] arr, int num) {
        for (int nums : arr) {
            if (nums == num)
                return true;
        }
        return false;
    }
}