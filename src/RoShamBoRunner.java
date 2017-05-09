

public class RoShamBoRunner
{
    public static void main(String[] args)
    {
        /*
        RoShamBoPlayer[] players = new RoShamBoPlayer[12];
        players[0] = new RoShamBoPlayerAdam("Adam");
        players[1] = new RoShamBoPlayerEmmet("Emmet");
        players[2] = new RoShamBoPlayerArman("Arman");
        players[3] = new RoShamBoPlayerShingo("Shingo");
        players[4] = new RoShamBoPlayerAkshay("Akshay");
        players[5] = new RoShamBoPlayerAnthony("Anthony");
        players[6] = new RoShamBoPlayerJoe("Joe");
        players[7] = new RoShamBoPlayerDawson("Dawson");
        players[8] = new RoShamBoPlayerIra("Ira");
        players[9] = new RoShamBoPlayerKevin("Kevin");
        players[10] = new RoShamBoPlayerMalcolm("Malcolm");
        players[11] = new RoShamBoPlayerTimmy("Timmy");
        */
        
        //  *** Add your player below to test that your code works!
        //  *** change numPlayers to 5
        //  *** add players[4] = new RoShamBoPlayerYourName("YourName");
        //  *** "YourName" might be "Arman" for example
        
        int numPlayers = 4;
        RoShamBoPlayer[] players = new RoShamBoPlayer[numPlayers];
        players[0] = new RoShamBoPlayerRandomBot("RandomBot");
        players[1] = new RoShamBoPlayerFrequencyBot("FrequencyBot");
        players[2] = new RoShamBoPlayerLastMoveBot("LastMoveBot");
        players[3] = new RoShamBoPlayerRockBot("RockBot");

        
        RoShamBoTournament tourney = new RoShamBoTournament(players);
        tourney.roundRobin();
        RoShamBoResults.displayResults();
        
    }
}
