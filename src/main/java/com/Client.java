package com;

import config.ClientConfig;

/**
 * Created by Robi on 2016.03.15..
 */
public class Client {
    public long clientId;
    public ClientConfig conf;

    public Client(long id){
        clientId = id;
        conf = new ClientConfig();
    }

    public Client(long id, ClientConfig conf){
        clientId = id;
        this.conf = conf;
    }

    public void updateConfig(ClientConfig conf) {
        this.conf.updateConfig(conf);
    }
}
