import java.io.*;
import java.util.*;

public class RoShamBoTournament
{
  
    private static RoShamBoPlayer[] players;
    private static int numRounds;
    private int roundNumber = 0;
    
    
    public RoShamBoTournament(RoShamBoPlayer[] players)
    {
        this.players = players;
        numRounds = 1000;
    }
    
    public void roundRobin()
    {
       
        
        
        
        for (int i = 0; i < players.length; i++)
        {
            for (int j = i+1; j < players.length; j++)
            {
                
       
                playMatch(players[i], players[j]);
                players[i].reset();
                players[j].reset();
            }
        }
        
        /*
        for (int i = 0; i < players.length; i++)
        {
            for (int j = i+1; j < players.length; j++)
            {
                RoShamBoPlayer player1 = new RoShamBoPlayer(players[i]);
                RoShamBoPlayer player2 = new RoShamBoPlayer(players[j]);
       
                playGame(player1, player2);
            }
        } */
        
        
        
        
    } // end method roundRobin
    
    public int getRound()
    {
     
        return roundNumber;
        
    }
    public void playMatch(RoShamBoPlayer player1, RoShamBoPlayer player2)
    {
        for (roundNumber = 0; roundNumber < numRounds; roundNumber++)
        {
            String p1move = player1.makeMove();
            player1.addMyMove(p1move);
            player2.addOpponentMove(p1move);
            
            String p2move = player2.makeMove();
            player2.addMyMove(p2move);
            player1.addOpponentMove(p2move);
            
        }
        
        // add to results
        RoShamBoResults.addResult(new RoShamBoMatch(player1, player2));
        
        
    }
    
    
    
        
        
}

    
    

    
    
    