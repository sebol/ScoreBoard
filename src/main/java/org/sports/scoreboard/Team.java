package org.sports.scoreboard;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class Team {
    private String name;

    @Setter(AccessLevel.PACKAGE)
    private int score;

    public Team(String name) {
        this.name = name;
    }
}
