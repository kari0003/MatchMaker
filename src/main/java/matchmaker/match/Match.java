package matchmaker.match;

import config.MatcherConfig;
import matchmaker.queue.QueueEntry;
import matchmaker.queue.QueueMatcher;

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

    public Match(int id, int teamCount, int teamSize){
        matchID = id;
        teams = new Team[teamCount];
        this.teamCount = 0;
        for(int i = 0; i < teamCount; i++){
            addTeam(new Team(i, teamSize));
        }
    }

    public void addTeam(Team team) {
        teams[teamCount] = team;
        teamCount++;
    }

    public Team getTeam(int i){
        return teams[i];
    }

    public int getTeamCount(){
        return teamCount;
    }

    /**
     * Based on team scores and distances. A match below strictness threshold is good to go.
     * @return score of the match based on player elo & rolster data
     */
    public double getMatchScore(String aspect){
        double[][] diffStat = new double[teams.length][teams.length];
        double[] avgDist = new double[teams.length];
        double maxDist = 0;
        double maxTeamDist = 0;
        for(int i = 0; i < teams.length; i++){
            avgDist[i] = 0;
            for(int j = i; j < teams.length; j++){
                diffStat[i][j] = Math.abs(teams[i].getTeamScore(aspect) - teams[j].getTeamScore(aspect));
                avgDist[i] += diffStat[i][j];
                if(diffStat[i][j] > maxDist){
                    maxDist = diffStat[i][j];
                }
            }
            avgDist[i] /= teams.length;
            /*
            if(maxTeamDist < teams[i].getTeamDist()){
                maxTeamDist = teams[i].getTeamDist();
            }
            */
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
    public double getMaxDistance(MatcherConfig matcher) {
        double dist = 0;
        for(String aspect : matcher.aspectNames) {
            double aspectDist = 0;
            for(int i = 0; i < teamCount; i++){
                for(int j = i+1; j< teamCount; j++){
                    if(matcher.considerAspect.get(aspect)) {
                        double thisDist = Math.abs(teams[i].getTeamScore(aspect) - teams[j].getTeamScore(aspect));
                        if (i == 0 && j == 1) {
                            aspectDist = thisDist;
                        }
                        if (aspectDist < thisDist) {
                            aspectDist = thisDist;
                        }
                    }
                }
            }
            dist += matcher.weighAspect.get(aspect) * aspectDist;
        }

        if(matcher.considerWait) {
            long minWait = -1;
            for (int i = 0; i < teamCount; i++) {
                long wait = teams[i].getTeamWaitingTime();
                if(i == 0 || (wait < minWait && wait != -1)) {
                    minWait = wait;
                };
            }
            double modifier = matcher.waitModifier/1000;
            double modification = Math.min(modifier*minWait, matcher.maxWaitModification);
            if(modification > 0 && (dist-modification) >= 0 ){
                dist -= modification;
            }
        }
        return dist;
    }

    public double getTeamDistWithRolster(int forTeam, QueueEntry rolster, MatcherConfig matcher) {
        double dist = -1;
        for(String aspect : matcher.aspectNames) {
            double aspectDist = 0;
            boolean isMaxDistSet = false;
            for (int i = 0; i < teamCount; i++) {
                double thisDist = Math.abs(teams[forTeam].getTeamScoreWithRolster(aspect, rolster) - teams[i].getTeamScore(aspect));
                if (i != forTeam) {
                    if (!isMaxDistSet) {
                        aspectDist = thisDist;
                        isMaxDistSet = true;
                    }
                    if (aspectDist < thisDist) {
                        aspectDist = thisDist;
                    }
                }
            }
            dist += aspectDist;
        }

        if(matcher.considerWait) {
            long minWait = -1;
            for (int i = 0; i < teamCount; i++) {
                long wait = teams[i].getTeamWaitingTime();
                if(i == 0 || (wait < minWait && wait != -1)) {
                    minWait = wait;
                };
            }
            double modifier = matcher.waitModifier/1000;
            double modification = Math.min(modifier*minWait, matcher.maxWaitModification);
            if(modification > 0 && (dist-modification) >= 0 ){
                dist -= modification;
            }
        }
        return dist;
    }
}
