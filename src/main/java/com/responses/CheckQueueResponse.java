package com.responses;

import com.requests.CheckQueueRequest;
import com.requests.CreateQueueRequest;
import matchmaker.match.Match;
import matchmaker.queue.QueueStatus;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.03.15..
 */
public class CheckQueueResponse {
    public QueueStatus status;
    public LinkedList<Match> matches;

    public CheckQueueResponse(LinkedList<Match> matches, QueueStatus status){
        this.status = status;
        this.matches = matches;
    }

    public CheckQueueResponse(){
        status = QueueStatus.INACTIVE;
        matches = new LinkedList<Match>();
    }
}
