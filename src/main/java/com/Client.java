package com;

import config.ClientConfig;

/**
 * Created by Robi on 2016.03.15..
 */
public class Client {
    public long clientId;
    public ClientConfig conf;

    public Client(){
        conf = new ClientConfig();
    }

    public Client(ClientConfig conf){
        this.conf = conf;
    }

}
