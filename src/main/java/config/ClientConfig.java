package config;

import matchmaker.queue.QueueEntry;

import java.util.HashMap;
import java.util.Map;

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
        for (Map.Entry<String, QueueConfig> entry : conf.queueConfigs.entrySet()) {
            String key = entry.getKey();
            if (queueConfigs.get(key) != null) {
                queueConfigs.get(key).updateConfig(entry.getValue());
                queueConfigs.put(key, entry.getValue()); //TODO: To be removed after queueConfig is updateable
            } else {
                queueConfigs.put(key, entry.getValue());
            }
        }
    }
}
