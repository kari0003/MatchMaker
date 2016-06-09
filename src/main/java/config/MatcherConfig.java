package config;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Robi on 2016.04.12..
 */
public class MatcherConfig {
    public MatcherType matcherType;
    public ArrayList<String> aspectNames = new ArrayList<String>();
    public HashMap<String, Boolean> considerAspect = new HashMap<String, Boolean>();
    public HashMap<String, Double> weighAspect = new HashMap<String, Double>();
    public int maxPotentials;
    public int maxTargets;
    public int maxDistancePlayers;
    public int maxDistanceTeams;
    public boolean considerWait;
    public double waitModifier;    //Distance points forgiven for 1s of waiting in the queue
    public double maxWaitModification;

    public MatcherConfig(){
        this(MatcherType.DEFAULT, 30, 1, 100, 300, 10, 100 );
    }

    public MatcherConfig(MatcherType type, int maxPotentials, int maxTargets, int maxDistancePlayers, int maxDistanceTeams){
        matcherType = type;
        this.maxDistancePlayers = maxDistancePlayers;
        this.maxTargets = maxTargets;
        this.maxPotentials = maxPotentials;
        this.maxDistanceTeams = maxDistanceTeams;
        waitModifier = 0;
        maxWaitModification = 0;
    }

    public MatcherConfig(MatcherType type, int maxPotentials, int maxTargets,
                         int maxDistancePlayers, int maxDistanceTeams,
                         int waitModifier, int maxWaitModification){
        matcherType = type;
        this.maxDistancePlayers = maxDistancePlayers;
        this.maxTargets = maxTargets;
        this.maxPotentials = maxPotentials;
        this.maxDistanceTeams = maxDistanceTeams;
        this.waitModifier = waitModifier;
        this.maxWaitModification = maxWaitModification;
    }

    public MatcherConfig(MatcherType type){
        this.matcherType = type;
    }

    public MatcherConfig limitPotentials(int maxPotentials, int maxTargets){
        this.maxPotentials = maxPotentials;
        this.maxTargets = maxTargets;
        return this;
    }

    public MatcherConfig addDistance(int maxDistancePlayers, int maxDistanceTeams){
        this.maxDistancePlayers = maxDistancePlayers;
        this.maxDistanceTeams = maxDistanceTeams;
        return this;
    }

    public MatcherConfig addWaiting(boolean considerWait, double waitModifier, double maxWaitModification){
        this.considerWait = considerWait;
        if(considerWait){
            this.waitModifier = waitModifier;
            this.maxWaitModification = maxWaitModification;
        }
        return this;
    }

    public MatcherConfig addAspect(String key, double weight, boolean isConsidered){
        aspectNames.add(key);
        considerAspect.put(key, isConsidered);
        weighAspect.put(key, weight);
        return this;
    }

}
