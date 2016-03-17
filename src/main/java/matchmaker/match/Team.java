package matchmaker.match;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Robi on 2016.02.26..
 * Represents a team of players. The number of players are defined by the Queue Configuration.
 */
public class Team{
    public final int teamId;
    public TeamMember[] members;
    private int memberCount;

    public Team(int id, int teamSize){
        teamId = id;
        memberCount = 0;
        members = new TeamMember[teamSize];
    }

    public Team(){
        teamId = -1;
    }

    public void addMember(TeamMember member) {
        members[memberCount] = member;
        memberCount += 1;
    }

    public int getTeamId() {
        return teamId;
    }
}
