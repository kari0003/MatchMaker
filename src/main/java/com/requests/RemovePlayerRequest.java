package com.requests;

import matchmaker.match.Player;

/**
 * Created by Robi on 2016.05.07..
 */
public class RemovePlayerRequest {
    public Player player;

    public RemovePlayerRequest(Player player){
        this.player = player;
    }
}
