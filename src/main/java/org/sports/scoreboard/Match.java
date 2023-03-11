package org.sports.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Match {
    private Team homeTeam;
    private Team awayTeam;
}
