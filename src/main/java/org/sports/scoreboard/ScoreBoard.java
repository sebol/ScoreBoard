package org.sports.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ScoreBoard {
    private AtomicLong idCounter = new AtomicLong();

    private MatchRepository repository = new MatchRepository();

    private ScoreBoardValidator validator = new ScoreBoardValidator();

    private ScoreBoardSorter sorter = new ScoreBoardSorter();

    private List<Match> matchesList = Collections.unmodifiableList(new ArrayList<>());

    public synchronized Match startGame(String homeTeamName, String awayTeamName) {
        validator.validateCreation(repository, homeTeamName, awayTeamName);
        Match match = new Match(nextId(), new Team(homeTeamName), new Team(awayTeamName));
        repository.save(match);
        updateState();
        return match;
    }

    public synchronized void updateScore(long matchId, int homeTeamScore, int awayTeamScore) {
        validator.validateUpdate(repository, matchId, homeTeamScore, awayTeamScore);
        Match match = repository.findMatchById(matchId).get();
        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
        repository.save(match);
        updateState();
    }

    public synchronized void finishGame(long matchId) {
        validator.validateDelete(repository, matchId);
        repository.delete(matchId);
        updateState();
    }

    public List<Match> getSummary() {
        return matchesList;
    }

    public Optional<Match> findMatchByTeamName(String teamName) {
        if (isEmpty(teamName)) {
            return Optional.empty();
        }
        return repository.findMatchByTeamName(teamName);
    }

    private long nextId() {
        return idCounter.incrementAndGet();
    }

    private void updateState() {
        matchesList = Collections.unmodifiableList(sorter.sort(repository.findAll()));
    }
}