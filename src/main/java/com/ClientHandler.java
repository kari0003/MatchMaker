package com;

import config.ClientConfig;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Robi on 2016.04.29..
 */
public class ClientHandler {
    private static long lastId = 0;
    private static LinkedList<Client> clients;

    public static void initialize() {
        lastId = 0;
        clients = new LinkedList<Client>();
    }

    public static long createClient(ClientConfig config){
        lastId++;
        clients.push(new Client(lastId, config));
        return lastId;
    }

    public static long addClient(Client c){
        lastId++;
        clients.push(c);
        c.clientId = lastId;
        return lastId;
    }

    public static Client getClient(long clientId) {
        for(Client c : clients){
            if(c.clientId == clientId){
                return c;
            }
        }
        return null;
    }

    public static LinkedList<Client> getClients() {
        return clients;
    }

    public static void deleteClient(long clientId) {
        for(Client c: clients){
            if(c.clientId == clientId){
                clients.remove(c);
                break;
            }
        }
    }
}
