import java.util.*;

public class RoShamBoPlayerFrequencyBot extends RoShamBoPlayer
{
    
    public RoShamBoPlayerFrequencyBot(String name)
    {
        super(name);
    }
    
    @Override
    public String makeMove()
    {
        
        int rocks = 0, scissors = 0, paper = 0;
        
        List<String> moves = getOpponentMoves();
        for (String move : moves)
        {   
            if (move.equals("rock")) 
               rocks++;
            else if (move.equals("scissors"))
               scissors++;
            else
                paper++;
        }
        
        if (rocks >= scissors && rocks >= paper)
            return "paper";
        else if (scissors >= paper && scissors >= rocks)
            return "rock";
        else return "scissors";
        
        
    }
              
}
    