package org.sports.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ScoreBoard {

    private static final String EXCEPTION_MESSAGE_NOT_FOUND = "Match with id %s does not exists!";

    private AtomicLong idCounter = new AtomicLong();

    private MatchRepository repository = new MatchRepository();

    private ScoreBoardSorter sorter = new ScoreBoardSorter();

    private List<Match> matchesList = Collections.unmodifiableList(new ArrayList<>());

    public Match startGame(String homeTeamName, String awayTeamName) {
        validateCreation(homeTeamName, awayTeamName);
        Match match = new Match(nextId(), new Team(homeTeamName), new Team(awayTeamName));
        repository.save(match);
        updateState();
        return match;
    }

    private long nextId() {
        return idCounter.incrementAndGet();
    }

    private void updateState() {
        matchesList = Collections.unmodifiableList(sorter.sort(repository.findAll()));
    }

    private void validateCreation(String homeTeamName, String awayTeamName) {
        //are names empty
        //are names the same?
        //are names already existing?
        //can the team be present on the board only once at the same time?

    }

    public void updateScore(long matchId, int homeTeamScore, int awayTeamScore) {
        validateUpdate(matchId, homeTeamScore, awayTeamScore);
        Match match = repository
                .findMatchById(matchId)
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format(EXCEPTION_MESSAGE_NOT_FOUND, matchId))
                );
        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
        repository.save(match);
        updateState();
    }

    private void validateUpdate(long matchId, int homeTeamScore, int awayTeamScore) {
        //can score be lower than before?
        //Should score be incremented by only one?
    }

    public List<Match> getSummary() {
        return matchesList;
    }

    public void finishGame(long matchId) {
        repository.delete(matchId);
        updateState();
    }

    public Optional<Match> findMatchByTeamName(String teamName) {
        if(isEmpty(teamName)) {
            return Optional.empty();
        }

        return repository.findMatchByTeamName(teamName);
    }
}
