package org.sports.scoreboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoardSorter {
    private Comparator<Match> compareByScoreSum = (m1, m2) -> (m2.getHomeTeam().getScore() + m2.getAwayTeam().getScore())
            - (m1.getHomeTeam().getScore() + m1.getAwayTeam().getScore());

    private Comparator<Match> compareByRecentlyAdded = Comparator.comparing(Match::getId).reversed();

    private Comparator<Match> fullComparator = compareByScoreSum.thenComparing(compareByRecentlyAdded);

    List<Match> sort(List<Match> list) {
        return list.stream().sorted(fullComparator).collect(Collectors.toList());
    }
}
