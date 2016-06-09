package matchmaker.match;

import config.MatcherConfig;
import matchmaker.queue.QueueEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
        if(memberCount < members.length) {
            members[memberCount] = member;
            memberCount += 1;
        }else{
            System.out.printf("Team is full!");
        }
    }

    public void addMember(QueueEntry found) {
        addMember(new TeamMember(memberCount, found));
    }


    public double getTeamScore(String aspect){
        double score = 0;
        if(memberCount > 0) {
            for (int i = 0; i < memberCount; i++) {
                TeamMember m = members[i];
                if(m != null) {
                    score += m.getScore(aspect);
                }
            }
        }
        return score;
    }

    public double getTeamDist(MatcherConfig config){
        double[][] diffStat = new double[members.length][members.length];
        double[] avgDist = new double[members.length];
        double maxDist = 0;
        for(int i = 0; i < memberCount; i++){
            avgDist[i] = 0;
            for(int j = i+1; j < memberCount; j++){
                diffStat[i][j] = members[i].getDist(members[j], config);
                avgDist[i] += diffStat[i][j];
                if(diffStat[i][j] > maxDist){
                    maxDist = diffStat[i][j];
                }
            }
            avgDist[i] /= members.length;
        }
        return maxDist;
    }

    public int getMemberCount(){
        return memberCount;
    }

    public int getTeamID() {
        return teamID;
    }

    public Collection<? extends QueueEntry> getQueueEntries() {
        ArrayList<QueueEntry> entries = new ArrayList<QueueEntry>();
        for(TeamMember m : members){
            entries.add(m.getQueueEntry());
        }
        return entries;
    }

    public double getTeamScoreWithRolster(String aspect, QueueEntry rolster) {
        double score = 0;
        score += getTeamScore(aspect);
        score += rolster.getScore(aspect);
        return score;
    }

    public long getTeamWaitingTime() {
        long minWait = -1;
        for(int i = 0; i < memberCount; i++){
            long waitingTime = members[i].getWaitingTime();
            if(i == 0 || waitingTime < minWait){
                minWait = waitingTime;
            }
        }
        return minWait;
    }
}
