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
    public Match[] matches;

    public CheckQueueResponse(LinkedList<Match> matches, QueueStatus status){
        this.status = status;
        this.matches = new Match[matches.size()];
        for(int i = 0; i < matches.size(); i++){
            this.matches[i] = matches.pop();
        }

    }

    public CheckQueueResponse(){
        status = QueueStatus.INACTIVE;
        //matches = new Match[0];
    }
}
