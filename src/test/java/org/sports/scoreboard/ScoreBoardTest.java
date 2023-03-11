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
        scoreBoard.updateScore(match0, 0, 5);
        scoreBoard.updateScore(match1, 10, 2);
        scoreBoard.updateScore(match2, 2, 2);
        scoreBoard.updateScore(match3, 6, 6);
        scoreBoard.updateScore(match4, 3, 1);


        summary = scoreBoard.getSummary();
        assertEquals(match3, summary.get(0));
        assertEquals(match1, summary.get(1));
        assertEquals(match0, summary.get(2));
        assertEquals(match4, summary.get(3));
        assertEquals(match2, summary.get(4));


        // Finish the game
        scoreBoard.finishGame(match4);
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
        String expectedTeamName = "Poland";
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.startGame(expectedTeamName, "Brazil");

        // when
        Optional<Match> existingMatch = scoreBoard.findMatchByTeamName(expectedTeamName);
        Optional<Match> notExistingMatch = scoreBoard.findMatchByTeamName("Hello");

        // then
        assertTrue(existingMatch.isPresent());
        assertTrue(expectedTeamName.equals(existingMatch.get().getAwayTeam()) || expectedTeamName.equals(existingMatch.get().getAwayTeam().getName()));

        assertFalse(notExistingMatch.isPresent());
    }

}