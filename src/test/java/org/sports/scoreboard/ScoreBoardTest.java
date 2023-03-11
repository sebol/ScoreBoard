package org.sports.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardTest {

    ScoreBoard scoreBoard;


    @BeforeEach
    void setup() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldBeInitialisedCorrectly() {
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void shouldCreateMatchCorrectly() {
        // given
        String homeTeamName = "Spain";
        String awayTeamName = "Brazil";

        // when
        Match match = scoreBoard.startGame(homeTeamName, awayTeamName);

        // then
        assertEquals(homeTeamName, match.getHomeTeam().getName());
        assertEquals(awayTeamName, match.getAwayTeam().getName());
        assertEquals(0, match.getHomeTeam().getScore());
        assertEquals(0, match.getAwayTeam().getScore());
    }

    @Test
    void shouldCreateAndStoreMatchCorrectly() {
        // given
        String homeTeamName = "Poland";
        String awayTeamName = "Brazil";

        // when
        scoreBoard.startGame(homeTeamName, awayTeamName);

        List<Match> summary = scoreBoard.getSummary();

        // then
        assertEquals(1, summary.size());
        assertEquals(homeTeamName, summary.get(0).getHomeTeam().getName());
        assertEquals(awayTeamName, summary.get(0).getAwayTeam().getName());
        assertEquals(0, summary.get(0).getHomeTeam().getScore());
        assertEquals(0, summary.get(0).getAwayTeam().getScore());
    }

    @Test
    void shouldUpdateMatchCorrectly() {
        String homeTeamName = "Spain";
        String awayTeamName = "Brazil";
        int homeScore = 10;
        int awayScore = 2;

        // when
        Match match = scoreBoard.startGame(homeTeamName, awayTeamName);
        scoreBoard.updateScore(match.getId(), homeScore, awayScore);

        // then
        assertEquals(homeTeamName, match.getHomeTeam().getName());
        assertEquals(awayTeamName, match.getAwayTeam().getName());
        assertEquals(homeScore, match.getHomeTeam().getScore());
        assertEquals(awayScore, match.getAwayTeam().getScore());
    }

    @Test
    void shouldFinishGameCorrectly()
    {
        // given
        Match matchToFinish = scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.startGame("Spain", "Brazil");

        // when
        scoreBoard.finishGame(matchToFinish.getId());
        List<Match> summary = scoreBoard.getSummary();

        // then
        assertEquals(1, summary.size());
    }

    @Test
    void shouldCorrectlyHandleHappyPath() {
        // Initialise the board
        Match match0 = scoreBoard.startGame("Mexico", "Canada");
        Match match1 = scoreBoard.startGame("Spain", "Brazil");
        Match match2 = scoreBoard.startGame("Germany", "France");
        Match match3 = scoreBoard.startGame("Uruguay", "Italy");
        Match match4 = scoreBoard.startGame("Argentina", "Australia");

        List<Match> summary = scoreBoard.getSummary();
        assertEquals(5, summary.size());

        // Update scores
        scoreBoard.updateScore(match0.getId(), 0, 5);
        scoreBoard.updateScore(match1.getId(), 10, 2);
        scoreBoard.updateScore(match2.getId(), 2, 2);
        scoreBoard.updateScore(match3.getId(), 6, 6);
        scoreBoard.updateScore(match4.getId(), 3, 1);


        summary = scoreBoard.getSummary();
        assertEquals(match3, summary.get(0));
        assertEquals(match1, summary.get(1));
        assertEquals(match0, summary.get(2));
        assertEquals(match4, summary.get(3));
        assertEquals(match2, summary.get(4));


        // Finish the game
        scoreBoard.finishGame(match4.getId());
        summary = scoreBoard.getSummary();
        assertEquals(4, summary.size());
        assertEquals(match3, summary.get(0));
        assertEquals(match1, summary.get(1));
        assertEquals(match0, summary.get(2));
        assertEquals(match2, summary.get(3));
    }

    @Test
    void shouldFindMatchByTeamName() {
        // given
        String expectedHomeTeamName = "Poland";
        String expectedAwayTeamName = "Germany";
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.startGame(expectedHomeTeamName, "Brazil");
        scoreBoard.startGame("Spain", expectedAwayTeamName);


        // when
        Optional<Match> existingHomeMatch = scoreBoard.findMatchByTeamName(expectedHomeTeamName);
        Optional<Match> existingAwayMatch = scoreBoard.findMatchByTeamName(expectedAwayTeamName);
        Optional<Match> notExistingMatch = scoreBoard.findMatchByTeamName("Hello");

        // then
        assertTrue(existingHomeMatch.isPresent());
        assertTrue(expectedHomeTeamName.equals(existingHomeMatch.get().getHomeTeam().getName()));

        assertTrue(existingAwayMatch.isPresent());
        assertTrue(expectedAwayTeamName.equals(existingAwayMatch.get().getAwayTeam().getName()));

        assertFalse(notExistingMatch.isPresent());
    }

}