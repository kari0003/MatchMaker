package com.configs;

/**
 * Created by Robi on 2016.03.10..
 */
public class QueueConfig {
    public int updateInterval = 1000;  //Time spent between the queue update its state in milliseconds.
    public int teamSize = 3;    //How many players does a team have.
    public int teamCount = 2;   //How many teams does a match have.

    public QueueConfig(int updateMillis, int teamSize, int teamCount ){
        updateInterval = updateMillis;
        this.teamCount = teamCount;
        this.teamSize = teamSize;
    }

    public QueueConfig(){
        updateInterval = 1000;
        teamSize = 3;
        teamCount = 2;
    }
}
