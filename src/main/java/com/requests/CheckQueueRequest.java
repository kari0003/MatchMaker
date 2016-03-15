package com.requests;

/**
 * Created by Robi on 2016.03.15..
 */
public class CheckQueueRequest {
    private static long lastId = 0;
    public final long queueId;

    public CheckQueueRequest(){
        lastId += 1;
        queueId = lastId;
    }
}
