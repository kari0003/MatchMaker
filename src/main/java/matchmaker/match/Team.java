package matchmaker.match;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Robi on 2016.02.26..
 * Represents a team of players. The number of players are defined by the Queue Configuration.
 */
public class Team implements Serializable{
    private final int teamID;
    private TeamMember[] members;
    private int memberCount;

    public Team(int id, int teamSize){
        teamID = id;
        memberCount = 0;
        members = new TeamMember[teamSize];
    }

    public void addMember(TeamMember member) {
        members[memberCount] = member;
        memberCount += 1;
    }

    public double getTeamScore(){
        double score = 0;
        for (TeamMember m : members) {
            score += m.getScore();
        }
        return score;
    }

    public double getTeamDist(){
        double[][] diffStat = new double[members.length][members.length];
        double[] avgDist = new double[members.length];
        double maxDist = 0;
        for(int i = 0; i < members.length; i++){
            avgDist[i] = 0;
            for(int j = i; j < members.length; j++){
                diffStat[i][j] = Math.abs(members[i].getScore() - members[j].getScore());
                avgDist[i] += diffStat[i][j];
                if(diffStat[i][j] > maxDist){
                    maxDist = diffStat[i][j];
                }
            }
            avgDist[i] /= members.length;
        }

        return maxDist;
    }

    public int getTeamID() {
        return teamID;
    }
}
