package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Robi on 2016.04.12..
 */
public class MatchConfig{
    public int teamSize = 3;    //How many players does a team have.
    public int teamCount = 2;   //How many teams does a match have.

    public MatchConfig(int teamSize, int teamCount) {
        this.teamCount = teamCount;
        this.teamSize  = teamSize;
    }

    public MatchConfig(){
        Properties basicConfig = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config/baseMatchConfig.config");

            basicConfig.load(input);

            if(basicConfig.containsKey("teamSize")) {
                teamSize = Integer.parseInt(basicConfig.getProperty("teamSize"));
            }
            if(basicConfig.containsKey("teamCount")) {
                teamCount = Integer.parseInt(basicConfig.getProperty("teamCount"));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
