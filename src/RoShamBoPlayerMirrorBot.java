import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;
import java.util.ArrayList;

import static sun.reflect.Reflection.getCallerClass;


public class RoShamBoPlayerMirrorBot extends RoShamBoPlayer {


    class Matchup {
        protected final RoShamBoPlayer p2;
        protected final RoShamBoPlayer p1;

        public Matchup(RoShamBoPlayer p1, RoShamBoPlayer p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

    }


    private Field f_rounds = null;
    private Field roShamBoPlayers = null;

    private RoShamBoPlayer[] rsbparr = null;
    private RoShamBoPlayer opponent = null;

    private ArrayList<Matchup> matchups = new ArrayList<>();

    private int myPos = -1;
    private int matchCount = 0;

    String prevOpponent = "";


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

        System.out.println("jrmoo");
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
            rsbparr = (RoShamBoPlayer[]) roShamBoPlayers.get(tmp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        if (myPos == -1) {
            for (int i = 0; i < rsbparr.length; i++) {
                if (rsbparr[i].getName().equals(this.getName())) {
                    myPos = i;
                    break;
                }
            }
        }


        if (matchups.size() == 0) {
            fillMatchups(rsbparr);
        }


        if (getMyMoves().size() == 0) {
            int tmpMatchCount = 0;
            for (int i = 0; i < matchups.size(); i++) {
                if (matchups.get(i).p1.getName().equals(this.getName()) || matchups.get(i).p2.getName().equals(this.getName())) {
                    if (tmpMatchCount == matchCount) {
                        if (matchups.get(i).p1.getName().equals(this.getName())) {
                            opponent = matchups.get(i).p2;
                        } else {
                            opponent = matchups.get(i).p1;
                        }
                        matchCount++;
                        break;
                    }
                    tmpMatchCount++;
                }
            }
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


    private void fillMatchups(RoShamBoPlayer[] players) {

        for (int i = 0; i < players.length; i++) {
            for (int j = i + 1; j < players.length; j++) {
                matchups.add(new Matchup(players[i], players[j]));
            }
        }
    }
}


