package matchmaker.match;

import matchmaker.queue.QueueEntry;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.*;

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

    public Team getTeam(int i){
        return teams[i];
    }

    public int getTeamCount(){
        return teams.length;
    }

    /**
     * Based on team scores and distances. A match below strictnesss treshold is good to go.
     * @return score of the match based on player elo & rolster data
     */
    public double getMatchScore(){
        double[][] diffStat = new double[teams.length][teams.length];
        double[] avgDist = new double[teams.length];
        double maxDist = 0;
        double maxTeamDist = 0;
        for(int i = 0; i < teams.length; i++){
            avgDist[i] = 0;
            for(int j = i; j < teams.length; j++){
                diffStat[i][j] = teams[i].getTeamScore() - teams[j].getTeamScore();
                avgDist[i] += diffStat[i][j];
                if(diffStat[i][j] > maxDist){
                    maxDist = diffStat[i][j];
                }
            }
            avgDist[i] /= teams.length;
            if(maxTeamDist < teams[i].getTeamDist()){
                maxTeamDist = teams[i].getTeamDist();
            }
        }
        return maxDist;
        //return maxDist/conf.maxMatchDist + maxTeamDist/conf.maxTeamDist;
    }

    public Collection<QueueEntry> getQueueEntries() {
        Collection<QueueEntry> entries = new LinkedList<>();
        for (Team t: teams) {
            entries.addAll(t.getQueueEntries());
        }
        return entries;
    }

    /**
     * Returns max distance amongst teams. (compares TeamScore)
     * @return the maximum distance between teams.
     */
    public double getMaxDistance() {
        double dist = 0;
        for(int i = 0; i < teamCount; i++){
            for(int j = i+1; j< teamCount; j++){
                if(i==0 && j==1){
                    dist = Math.abs(teams[i].getTeamScore() - teams[j].getTeamScore());
                }
                if(dist < Math.abs(teams[i].getTeamScore() - teams[j].getTeamScore())){
                    dist = Math.abs(teams[i].getTeamScore() - teams[j].getTeamScore());
                }
            }
        }
        return dist;
    }
}
