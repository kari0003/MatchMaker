package config;

import java.util.HashMap;

/**
 * Created by Robi on 2016.04.12..
 */
public class MatcherConfig {
    public MatcherType matcherType;
    public HashMap<String, Boolean> considerAspect = new HashMap<String, Boolean>();
    public HashMap<String, Double> weighAspect = new HashMap<String, Double>();
    public int maxPotentials;
    public int maxTargets;
    public int maxDistancePlayers;
    public int maxDistanceTeams;
    public int idealDistancePlayers;
    public int idealDistanceTeams;
    public int waitModifier;    //Distance points forgiven for 1s of waiting in the queue
    public int maxWaitModification;

    public MatcherConfig(){
        this(MatcherType.DEFAULT, 30, 1, 100, 300, 10, 30, 10, 100 );
    }

    public MatcherConfig(MatcherType type, int maxPotentials, int maxTargets, int maxDistancePlayers, int maxDistanceTeams){
        matcherType = type;
        this.maxDistancePlayers = maxDistancePlayers;
        this.maxTargets = maxTargets;
        this.maxPotentials = maxPotentials;
        this.maxDistanceTeams = maxDistanceTeams;
        idealDistancePlayers = 0;
        idealDistanceTeams = 0;
        waitModifier = 0;
        maxWaitModification = 0;
    }

    public MatcherConfig(MatcherType type, int maxPotentials, int maxTargets,
                         int maxDistancePlayers, int maxDistanceTeams,
                         int idealDistancePlayers, int idealDistanceTeams,
                         int waitModifier, int maxWaitModification){
        matcherType = type;
        this.maxDistancePlayers = maxDistancePlayers;
        this.maxTargets = maxTargets;
        this.maxPotentials = maxPotentials;
        this.maxDistanceTeams = maxDistanceTeams;
        this.idealDistancePlayers = idealDistancePlayers;
        this.idealDistanceTeams = idealDistanceTeams;
        this.waitModifier = waitModifier;
        this.maxWaitModification = maxWaitModification;
    }

    public void considerElo(double weight){
        considerAspect.put("elo", true);
        weighAspect.put("elo", weight);
    }

}
