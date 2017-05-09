

import java.util.*;

public class RoShamBoResults
{
    private static List<RoShamBoMatch> results = new ArrayList<RoShamBoMatch>();
    
    public static void addResult(RoShamBoMatch match)
    {
        results.add(match);
    }
    
    public static void displayResults()
    {
        System.out.println("********* Results *********");

        System.out.println("---------------------------");
        System.out.println("P1 wins   ties       P2wins");
        System.out.println("---------------------------");
        
        
        for (int i = 0; i < results.size(); i++)
        {
            RoShamBoMatch match = results.get(i);
            System.out.println(match.getPlayer1().getName() + "      vs     "
              
               + match.getPlayer2().getName());
            
            System.out.println(match.getPlayer1Wins() + "     " +
                               match.getTies()  +  "      " +
                               match.getPlayer2Wins());
            System.out.println("---------------------------");
        }
            
            
            
    }
    
    // tally up player win totals
    public static void displayRankings()
    {
        // not finished yet
        
        if (results.size() == 0)  // no matches to tally!
            return;
        List<RoShamBoPlayer> players = new ArrayList<RoShamBoPlayer>();
        
        // add first two players from first match
        
        RoShamBoMatch firstMatch = results.get(0);
        players.add(firstMatch.getPlayer1());
        players.add(firstMatch.getPlayer2());
        
        
        
        for (int i = 0; i < players.size(); i++)
        {
            //find player
        }
        
    }
   
    
    
}