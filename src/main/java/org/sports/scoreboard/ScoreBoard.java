package org.sports.scoreboard;

import java.util.List;
import java.util.Optional;

public class ScoreBoard {
    public Match startGame(String homeTeamName, String awayTeamName) {
        validateCreation(homeTeamName, awayTeamName);
        return new Match(new Team(homeTeamName), new Team(awayTeamName));
    }

    private void validateCreation(String homeTeamName, String awayTeamName) {
        //are names empty
        //are names the same?
        //are names already existing?
        //can the team be present on the board only once at the same time?

    }

    public void updateScore(Match match, int homeTeamScore, int awayTeamScore) {
        //can score be lower than before?
        //Should score be incremented by only one?
    }

    public List<Match> getSummary() {

        return null;
    }

    public void finishGame(Match match) {

    }

    public Optional<Match> findMatchByTeamName(String expectedTeamName) {
        // Assuming particular team can only be present once in the board, we can search match by only one team name
        return null;
    }
}
