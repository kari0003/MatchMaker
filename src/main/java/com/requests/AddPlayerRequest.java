package com.requests;

import matchmaker.match.Player;

/**
 * Created by Robi on 2016.03.15..
 */
public class AddPlayerRequest {
    public final long queueId;
    public final Player player;

    public AddPlayerRequest(){
        queueId = -1;
        player = new Player();
    }

    public AddPlayerRequest(long queueId, Player player){
        this.queueId = queueId;
        this.player = player;
    }
}
