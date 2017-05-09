// keeps a record of a match
public class RoShamBoMatch
{
    private RoShamBoPlayer player1;
    private RoShamBoPlayer player2;
    private int player1Wins = 0;
    private int player1Losses = 0;
    private int player2Wins = 0;
    private int player2Losses= 0;
    private int ties = 0;
    
    public RoShamBoMatch(RoShamBoPlayer p1, RoShamBoPlayer p2)
    {
        player1 = p1;
        player2 = p2;
        calculateWinsLosses(); 
        
    }
    
    public RoShamBoPlayer getPlayer1()
    {
        return player1;
    }
    
    public RoShamBoPlayer getPlayer2()
    {
        return player2;
    }
    
    
    public int getPlayer1Wins()
    {
        return player1Wins;
    }
   
    public int getPlayer1Losses()
    {
        return player1Losses;
    }
    
    public int getPlayer2Wins()
    {
        return player2Wins;
    }
   
    public int getPlayer2Losses()
    {
        return player2Losses;
    }
    
    public int getTies()
    {
        return ties;
    }
    
    
    
    
    private void calculateWinsLosses()
    {
        for (int i = 0; i < player1.getMyMoves().size(); i++)
        {
            String p1Move = player1.getMyMoves().get(i);
            String p2Move = player2.getMyMoves().get(i);
            int compare = p1Move.compareTo(p2Move);
            if (compare == 0) //tie
            {
                ties++;    
            }
            else if (compare == -2 || compare == -1 || compare ==  3) //p1wins
            { 
                player1Wins++;
                player2Losses++;  
            }
            else  // p2wins
            {
                player1Losses++;
                player2Wins++;
            } 
           
            
        }
    }
    
        
}