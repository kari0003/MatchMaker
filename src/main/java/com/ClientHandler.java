package com;

import config.ClientConfig;

import java.util.HashMap;

/**
 * Created by Robi on 2016.04.29..
 */
public class ClientHandler {
    private static long lastId = 0;
    private static HashMap<Long, Client> clients = new HashMap<Long, Client>();

    public static void initialize() {
        lastId = 0;
        clients = new HashMap<Long, Client>();
    }

    public static long createClient(ClientConfig config){
        lastId++;
        clients.put(lastId, new Client(config));
        return lastId;
    }

    public static long addClient(Client c){
        lastId++;
        clients.put(lastId, c);
        return lastId;
    }

    public static Client getClient(long clientId) {
        return clients.get(clientId);
    }

}
