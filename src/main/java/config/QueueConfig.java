package config;

import matchmaker.queue.matcher.RolsterMatcher;

import java.util.HashMap;

/**
 * Created by Robi on 2016.03.10..
 */
public class QueueConfig {
    public int updateInterval = 1000;  //Time spent between the queue update its state in milliseconds.
    public boolean updateOnInsert = true; //The queue updates it's status when a new entry is inserted.
    public HashMap<String, MatcherConfig> matcherConfigs = new HashMap<String, MatcherConfig>();
    public HashMap<String, MatchConfig> matchConfigs = new HashMap<String, MatchConfig>();

    public QueueConfig(int updateMillis, int teamSize, int teamCount ){
        updateInterval = updateMillis;
        matcherConfigs.put("test", new MatcherConfig());
        matcherConfigs.get("test").matcherType = MatcherType.ROLSTER_MATCHER;
        matchConfigs.put("test", new MatchConfig(teamSize, teamCount));
    }

    public QueueConfig(){
        this(1000, 3, 2);
    }
}
