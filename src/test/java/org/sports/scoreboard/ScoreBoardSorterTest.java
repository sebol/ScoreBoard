package org.sports.scoreboard;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardSorterTest {

    ScoreBoardSorter sorter = new ScoreBoardSorter();

    @Test
    void shouldCorrectlySortMatches() {
        // given
        List<Match> list = createList();

        list.get(1).getAwayTeam().setScore(3);

        List<Match> sorted = sorter.sort(list);

        assertTrue(sorted.get(0).getId() == 2);
        assertTrue(sorted.get(1).getId() == 3);
        assertTrue(sorted.get(2).getId() == 1);
    }

    private List<Match> createList() {
        List<Match> list = new ArrayList<>();
        list.add(new Match(1, new Team("A"), new Team("B")));
        list.add(new Match(2, new Team("C"), new Team("D")));
        list.add(new Match(3, new Team("E"), new Team("F")));
        return list;
    }

}