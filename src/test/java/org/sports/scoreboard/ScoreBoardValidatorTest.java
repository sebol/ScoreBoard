package org.sports.scoreboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.sports.scoreboard.ScoreBoardValidator.ERROR_MATCH_NOT_FOUND;
import static org.sports.scoreboard.ScoreBoardValidator.ERROR_SCORE_NEGATIVE;
import static org.sports.scoreboard.ScoreBoardValidator.ERROR_TEAM_ALREADY_EXISTS;
import static org.sports.scoreboard.ScoreBoardValidator.ERROR_TEAM_NAME_EMPTY;
import static org.sports.scoreboard.ScoreBoardValidator.ERROR_TEAM_NAME_THE_SAME;

@ExtendWith(MockitoExtension.class)
class ScoreBoardValidatorTest {

    @Mock
    MatchRepository repository;

    ScoreBoardValidator validator = new ScoreBoardValidator();

    @Test
    void shouldCheckForNullTeamNames() {

        Exception exceptionWhenHomeNameNotValid = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreation(repository, null, "A");
        });

        assertEquals(ERROR_TEAM_NAME_EMPTY, exceptionWhenHomeNameNotValid.getMessage());

        Exception exceptionWhenAwayNameNotValid = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreation(repository, "B", null);
        });

        assertEquals(ERROR_TEAM_NAME_EMPTY, exceptionWhenAwayNameNotValid.getMessage());
    }

    @Test
    void shouldCheckForBlankTeamNames() {

        Exception exceptionWhenHomeNameNotValid = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreation(repository, "", "A");
        });

        assertEquals(ERROR_TEAM_NAME_EMPTY, exceptionWhenHomeNameNotValid.getMessage());

        Exception exceptionWhenAwayNameNotValid = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreation(repository, "B", "");
        });

        assertEquals(ERROR_TEAM_NAME_EMPTY, exceptionWhenAwayNameNotValid.getMessage());
    }

    @Test
    void shouldCheckForTheSameNames() {
        String name = "Poland";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreation(repository, name, name);
        });

        assertEquals(ERROR_TEAM_NAME_THE_SAME, exception.getMessage());
    }

    @Test
    void shouldCheckIfTeamAlreadyExists() {
        //given
        when(repository.findMatchByTeamName(anyString())).thenReturn(Optional.of(new Match(2, null, null)));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreation(repository, "Poland", "Brazil");
        });

        // then
        assertEquals(ERROR_TEAM_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void shouldCheckForCorrectScoreValues() {
        Exception exceptionWhenHomeScoreNotValid = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateUpdate(repository, 3, -1, 5);
        });

        assertEquals(ERROR_SCORE_NEGATIVE, exceptionWhenHomeScoreNotValid.getMessage());

        Exception exceptionWhenAwayScoreNotValid = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateUpdate(repository, 3, 1, -5);
        });

        assertEquals(ERROR_SCORE_NEGATIVE, exceptionWhenAwayScoreNotValid.getMessage());
    }

    @Test
    void shouldCheckIfMatchExistsDuringUpdate() {
        //given
        long matchId = 5;
        when(repository.findMatchById(anyLong())).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateUpdate(repository, matchId, 3, 4);
        });

        // then
        assertEquals(String.format(ERROR_MATCH_NOT_FOUND, matchId), exception.getMessage());
    }

    @Test
    void shouldCheckIfMatchExistsDuringFinish() {
        //given
        long matchId = 5;
        when(repository.findMatchById(anyLong())).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateDelete(repository, matchId);
        });

        // then
        assertEquals(String.format(ERROR_MATCH_NOT_FOUND, matchId), exception.getMessage());
    }

}