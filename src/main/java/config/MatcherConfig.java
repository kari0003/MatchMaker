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
        matcherType = MatcherType.DEFAULT;
        considerAspect.put("elo", true);
        considerAspect.put("time", true);
        considerAspect.put("ping", false);

        weighAspect.put("elo", 0.7);
        weighAspect.put("time", 0.3);

        maxPotentials = 30;
        maxTargets = 1;
        maxDistancePlayers = 100;
        maxDistanceTeams = 300;
    }

}
