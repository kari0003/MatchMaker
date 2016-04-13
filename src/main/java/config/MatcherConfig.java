package config;

import java.util.HashMap;

/**
 * Created by Robi on 2016.04.12..
 */
public class MatcherConfig {
    public HashMap<String, Boolean> considerAspect = new HashMap<String, Boolean>();
    public HashMap<String, Double> weighAspect = new HashMap<String, Double>();

    public MatcherConfig(){
        considerAspect.put("elo", true);
        considerAspect.put("time", true);
        considerAspect.put("ping", false);

        weighAspect.put("elo", 0.7);
        weighAspect.put("time", 0.3);
    }

}
