package config;

import matchmaker.queue.matcher.RolsterMatcher;

import java.util.HashMap;

/**
 * Created by Robi on 2016.03.10..
 */
public class QueueConfig {
    public int updateInterval = 1000;  //Time spent between the queue update its state in milliseconds.
    public boolean updateOnInsert = true; //The queue updates it's status when a new entry is inserted.
    public boolean updateWhenChecked = true; //Update queue before a request checks its state.
    public MatcherConfig matcherConfig;
    public MatchConfig matchConfig;

    public QueueConfig(int updateMillis, int teamSize, int teamCount ){
        updateInterval = updateMillis;
        matcherConfig = new MatcherConfig();
        matcherConfig.matcherType = MatcherType.ROLSTER_MATCHER;
        matchConfig = new MatchConfig(teamSize, teamCount);
    }

    public QueueConfig(int updateMillis, MatcherConfig matcher, MatchConfig match ){
        updateInterval = updateMillis;
        matcherConfig = matcher;
        matchConfig = match;
    }

    public QueueConfig(){
        this(1000, 3, 2);
    }

    public QueueConfig setUpdate(int updateMillis, boolean onInsert, boolean beforeChecked){
        updateInterval = updateMillis;
        updateOnInsert = onInsert;
        updateWhenChecked = beforeChecked;
        return this;
    }

    public QueueConfig setTeams(int teamSize, int teamCount){
        matchConfig.teamCount = teamCount;
        matchConfig.teamSize = teamSize;
        return this;
    }

    public void updateConfig(QueueConfig config) {
        //TODO
    }
}
