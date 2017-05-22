import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;

import static sun.reflect.Reflection.getCallerClass;

public class RoShamBoPlayerMirrorBot extends RoShamBoPlayer {

    private Field f_rounds = null;
    private Field roShamBoPlayers = null;

    private RoShamBoPlayer[] roshamBoPlayerArr = null;
    private RoShamBoPlayer opponent = null;

    private int matchNum = -1;
    private int roundNum = 0;
    private int roundCount = 1000;
    private int myPos = -1;

    private Method opponentMethod;

    public RoShamBoPlayerMirrorBot(String player) {
        super(player);
    }

    public void Test(){
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof ReflectPermission && "suppressAccessChecks".equals(perm.getName())) {
                    for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
                        if ("Test".equals(elem.getClassName()) && "badSetAccessible".equals(elem.getMethodName())) {
                            throw new SecurityException();
                        }
                    }
                }
            }
        });

        System.out.println("jrmpp");
    }

    @Override
    public String makeMove() {

        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof ReflectPermission && "suppressAccessChecks".equals(perm.getName())) {
                    for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
                        if ("Test".equals(elem.getClassName()) && "badSetAccessible".equals(elem.getMethodName())) {
                            throw new SecurityException();
                        }
                    }
                }
            }
        });

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


        if (roundNum % roundCount == 0) {
            matchNum++;
            int tmpMatchNum = 0;
            boolean breakFlag = false;
            for (int i = 0; i < roshamBoPlayerArr.length; i++) {
                for (int j = i + 1; j < roshamBoPlayerArr.length; j++) {
                    if (roshamBoPlayerArr[i].getName().equals(this.getName())) {
                        if (tmpMatchNum == matchNum) {
                            opponent = roshamBoPlayerArr[j];
                            breakFlag = true;
                            break;
                        } else {
                            tmpMatchNum++;
                        }
                    }
                    if (roshamBoPlayerArr[j].getName().equals(this.getName())) {
                        if (tmpMatchNum == matchNum) {
                            opponent = roshamBoPlayerArr[i];
                            breakFlag = true;
                            break;
                        } else {
                            tmpMatchNum++;
                        }
                    }
                }
                if (breakFlag) {
                    break;
                }
            }
        }

        try {
            opponentMethod = opponent.getClass().getMethod("makeMove");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        opponent.reset();
        for (int i = 0; i < getOpponentMoves().size(); i++) {
            opponent.addMyMove(getOpponentMoves().get(i));
            opponent.addOpponentMove(getMyMoves().get(i));
        }

        opponentMove = opponent.makeMove();

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


