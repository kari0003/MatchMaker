package matchmaker.match;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Robi on 2016.02.26..
 * Represents a match, the result of a search.
 * Contains the teams of the match. The number of teams are specified by the Queue configuration
 *
 */
public class Match{
    public final int matchId;
    public Team[] teams;
    private int teamCount;

    public Match(int id, int teamSize){
        matchId = id;
        teamCount = 0;
        teams = new Team[teamSize];
    }

    public Match(){
        matchId = -1;
    }

    public void addTeam(Team team) {
        teams[teamCount] = team;
        teamCount++;
    }
}
