package matchmaker.match;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Robi on 2016.02.26..
 * Represents a match, the result of a search.
 * Contains the teams of the match. The number of teams are specified by the Queue configuration
 *
 */
public class Match implements Serializable{
    private final int matchID;
    private Team[] teams;
    private int teamCount;

    public Match(int id, int teamSize){
        matchID = id;
        teamCount = 0;
        teams = new Team[teamSize];
    }

    public void addTeam(Team team) {
        teams[teamCount] = team;
        teamCount++;
    }
}
