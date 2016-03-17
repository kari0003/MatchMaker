package com.requests;

import com.configs.QueueConfig;

/**
 * Created by Robi on 2016.03.10..
 */
public class CreateQueueRequest {
    private long clientId;
    private QueueConfig config;

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setConfig(QueueConfig config) {
        this.config = config;
    }

    public long getClientId() {
        return clientId;
    }

    public QueueConfig getConfig() {
        return config;
    }

    public CreateQueueRequest(long id, QueueConfig config){
        this.clientId = id;
        this.config = config;
    }

    public CreateQueueRequest(long id, int updateMillis, int teamSize, int teamCount){
        this.clientId = id;
        this.config = new QueueConfig(updateMillis, teamSize,teamCount);
    }

    public CreateQueueRequest(long id){
        this.clientId = id;
        this.config = new QueueConfig(100, 3, 2);
    }

    public CreateQueueRequest(){
        clientId = -1;
        config = new QueueConfig();
    }
}
