package org.sports.scoreboard;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ScoreBoardValidator {

    static final String ERROR_TEAM_NAME_EMPTY = "Team name is null or empty!";
    static final String ERROR_TEAM_NAME_THE_SAME = "Name of home team nad away team are the same!";
    static final String ERROR_TEAM_ALREADY_EXISTS = "Team with that name already exists on the board!";
    static final String ERROR_SCORE_NEGATIVE = "Score cannot be negative!";
    static final String ERROR_MATCH_NOT_FOUND = "Match with id %s does not exists!";

    void validateCreation(MatchRepository repository, String homeName, String awayName) {
        if (isEmpty(homeName) || isEmpty(awayName)) {
            throw new IllegalArgumentException(ERROR_TEAM_NAME_EMPTY);
        }

        if (homeName.equals(awayName)) {
            throw new IllegalArgumentException(ERROR_TEAM_NAME_THE_SAME);
        }

        if (repository.findMatchByTeamName(homeName).isPresent() || repository.findMatchByTeamName(awayName).isPresent()) {
            throw new IllegalArgumentException(ERROR_TEAM_ALREADY_EXISTS);
        }
    }

    void validateUpdate(MatchRepository repository, long matchId, int homeTeamScore, int awayTeamScore) {

        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException(ERROR_SCORE_NEGATIVE);
        }

        repository.findMatchById(matchId).orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_MATCH_NOT_FOUND, matchId)));
    }

    public void validateDelete(MatchRepository repository, long matchId) {
        repository.findMatchById(matchId).orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_MATCH_NOT_FOUND, matchId)));
    }
}
