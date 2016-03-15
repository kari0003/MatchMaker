package com;

/**
 * Created by Robi on 2016.03.15..
 */
public class Client {
    private static long lastId = 0;
    public long clientId;

    public Client(){
        lastId += 1;
        clientId = lastId;
    }
}
