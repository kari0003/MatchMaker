package config;

import java.util.HashMap;

/**
 * Created by Robi on 2016.03.10..
 */
public class QueueConfig {
    public int updateInterval = 1000;  //Time spent between the queue update its state in milliseconds.
    public MatcherConfig matcherConfig = new MatcherConfig();
    public HashMap<String, MatchConfig> matchConfigs = new HashMap<String, MatchConfig>();

    public QueueConfig(int updateMillis, int teamSize, int teamCount ){
        updateInterval = updateMillis;
        matchConfigs.put("test", new MatchConfig(teamSize, teamCount));
    }

    public QueueConfig(){
        updateInterval = 1000;
    }
}
