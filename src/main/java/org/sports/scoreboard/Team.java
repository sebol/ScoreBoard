package org.sports.scoreboard;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Team {
    private String name;

    @Setter(AccessLevel.PACKAGE)
    private int score;

    Team(String name) {
        this.name = name;
    }
}
