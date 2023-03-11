package org.sports.scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MatchRepository {

    private Map<Long, Match> matchesList = new HashMap<>();

    public void save(Match match) {
        matchesList.put(match.getId(), match);
    }

    public Optional<Match> findMatchById(long id) {
        return matchesList.containsKey(id) ? Optional.of(matchesList.get(id)) : Optional.empty();
    }

    public List<Match> findAll() {
        return matchesList.values().stream().toList();
    }

    public Optional<Match> findMatchByTeamName(String teamName) {
        return matchesList
                .values()
                .stream()
                .filter(match -> teamName.equals(match.getHomeTeam().getName())
                        || teamName.equals(match.getAwayTeam().getName()))
                .findFirst();
    }

    public void delete(long id) {
        matchesList.remove(id);
    }
}
