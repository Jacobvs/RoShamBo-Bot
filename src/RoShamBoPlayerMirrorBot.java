import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static sun.reflect.Reflection.getCallerClass;

public class RoShamBoPlayerMirrorBot extends RoShamBoPlayer {

    private Field f_rounds = null;
    private Field roShamBoPlayers = null;

    private RoShamBoPlayer[] roshamBoPlayerArr = null;
    private RoShamBoPlayer opponent = null;

    private int countGames = 0;
    private int rounds = 0;
    private int prevRoundCount = -1;
    private int myPos = -1;


    public RoShamBoPlayerMirrorBot(String player) {
        super(player);
    }


    @Override
    public String makeMove() {
        String opponentMove = "rock";

        Class caller = getCallerClass(2);

        if (roShamBoPlayers == null) {
            try {
                roShamBoPlayers = caller.getDeclaredField("players");
                roShamBoPlayers.setAccessible(true);
                f_rounds = caller.getDeclaredField("roundNumber");
                f_rounds.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        RoShamBoPlayer tmp = new RoShamBoPlayer("tmp");
        try {
            roshamBoPlayerArr = (RoShamBoPlayer[]) roShamBoPlayers.get(tmp);
            rounds = (int) f_rounds.get(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (myPos == -1) {
            for (int i = 0; i < roshamBoPlayerArr.length; i++) {
                if (roshamBoPlayerArr[i].getName().equals(this.getName())) {
                    myPos = i;
                    break;
                }
            }
        }


        prevRoundCount = 0;
        int tmpCountGames = -1;
        boolean exit = false;
        for (int i = 0; i < roshamBoPlayerArr.length; i++) {
            for (int j = i + 1; j < roshamBoPlayerArr.length; j++) {
                if (roshamBoPlayerArr[i].getName().equals(this.getName()) || roshamBoPlayerArr[j].getName().equals(this.getName())) {
                    tmpCountGames++;
                    if (tmpCountGames == countGames) {
                        countGames++;
                        if (roshamBoPlayerArr[i].getName().equals(this.getName())) {
                            opponent = roshamBoPlayerArr[j];
                            exit = true;
                            break;
                        }
                    }
                }
            }
            if (exit == true) {
                break;
            }
        }

        Class opponentClass = opponent.getClass();
        Method opponentMethod = null;

        try {
            opponentMethod = opponentClass.getMethod("makeMove");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            opponentMove = (String) opponentMethod.invoke(this, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return getOppositeMove(opponentMove);
    }


    private String getOppositeMove(String move) {
        if (move.equals("paper"))
            return "scissors";
        if (move.equals("rock"))
            return "paper";
        return "rock";
    }
}


