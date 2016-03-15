package matchmaker.match;

import java.util.ArrayList;

/**
 * Created by Robi on 2016.02.26..
 * Represents a match, the result of a search.
 * Contains the teams of the match. The number of teams are specified by the Queue configuration
 *
 */
public class Match {
    private int matchID;
    private ArrayList<Team> teams;
    private int teamCount;

    public Match(int id){
        matchID = id;
        teamCount = 0;
        teams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        teams.add(teamCount, team);
        teamCount++;
    }
}
