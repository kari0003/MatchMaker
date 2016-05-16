package config;

import java.util.HashMap;

/**
 * Created by Robi on 2016.04.12..
 *
 * Contains full config of the application.
 */
public class ClientConfig {
    public HashMap<String, QueueConfig> queueConfigs = new HashMap<String, QueueConfig>();

    public ClientConfig(){
        queueConfigs.put("test", new QueueConfig());
    }

    public String addQueue(String key, QueueConfig queueConfig){
        queueConfigs.put(key, queueConfig);
        return key;
    }

    public void updateConfig(ClientConfig conf) {
        //TODO
        queueConfigs = conf.queueConfigs;
    }
}
