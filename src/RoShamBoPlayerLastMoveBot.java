public class RoShamBoPlayerLastMoveBot extends RoShamBoPlayer
{
    
    public RoShamBoPlayerLastMoveBot(String name)
    {
        super(name);
    }
    
    @Override
    public String makeMove()
    {
        //matches opponent's last move
        int size = getOpponentMoves().size();
        if (size == 0)
            return "rock";    
        else
            return getOpponentMoves().get(size-1);
        
    }
    
}