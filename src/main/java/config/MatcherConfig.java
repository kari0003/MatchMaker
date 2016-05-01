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
    public int maxDistanceTeams;
    public int maxDistancePlayers;

    public MatcherConfig(){
        this(MatcherType.DEFAULT, 30, 1, 100, 300);

        maxPotentials = 30;
        maxTargets = 1;
        maxDistancePlayers = 100;
        maxDistanceTeams = 300;
    }

    public MatcherConfig(MatcherType type, int maxPotentials, int maxTargets, int maxDistancePlayers, int maxDistanceTeams){
        matcherType = type;
        this.maxDistancePlayers = maxDistancePlayers;
        this.maxTargets = maxTargets;
        this.maxPotentials = maxPotentials;
        this.maxDistanceTeams = maxDistanceTeams;
    }

    public void considerElo(double weight){
        considerAspect.put("elo", true);
        weighAspect.put("elo", weight);

    }

}
