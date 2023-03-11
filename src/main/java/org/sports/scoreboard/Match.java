package org.sports.scoreboard;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Match {
    private long id;
    private Team homeTeam;
    private Team awayTeam;
}
