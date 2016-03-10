package matchmaker.match;

/**
 * Created by Robi on 2016.02.26..
 * Represents a team of players. The number of players are defined by the Queue Configuration.
 */
public class Team {
    private final int teamID;
    private TeamMember[] members;
    private int memberCount;

    public Team(int id){
        teamID = id;
        memberCount = 0;
    }

    public void addMember(TeamMember member) {
        members[memberCount] = member;
        memberCount += 1;
    }

    public int getTeamID() {
        return teamID;
    }
}
