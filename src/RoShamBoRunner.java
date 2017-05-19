

public class RoShamBoRunner {
    public static void main(String[] args) {
        int numPlayers = 7;
        RoShamBoPlayer[] players = new RoShamBoPlayer[numPlayers];
        players[0] = new RoShamBoPlayerRandomBot("RandomBot");
        players[1] = new RoShamBoPlayerFrequencyBot("FrequencyBot");
        players[2] = new RoShamBoPlayerLastMoveBot("LastMoveBot");
        players[3] = new RoShamBoPlayerRockBot("RockBot");
        players[4] = new RoShamBoPlayerMirrorBot("MirrorBot");
        players[5] = new RoShamBoPlayerMax("MaxBot");
        players[6] = new RoShamBoPlayerMatthew("MatthewBot");


        RoShamBoTournament tourney = new RoShamBoTournament(players);
        tourney.roundRobin();
        RoShamBoResults.displayResults();

    }
}
