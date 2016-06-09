package matchmaker.match;

import config.MatcherConfig;
import matchmaker.queue.QueueEntry;

import java.io.Serializable;

/**
 * Created by Robi on 2016.02.26..
 * Represents a member of a team of a match. The team member has the useful information regards the player's position in the match
 */
public class TeamMember implements Serializable{
    private int teamPosition;
    private QueueEntry rolster;

    public TeamMember(int teamPosition, QueueEntry entry){
        this.teamPosition = teamPosition;
        this.rolster = entry;
    }

    public double getDist(TeamMember from, MatcherConfig conf){
        return rolster.getDist(from.getQueueEntry(), conf);
    }

    public double getScore(String key){
        if(rolster == null){
            System.out.printf("buggery!");
            return 0;
        }
        return rolster.getScore(key);
    }

    public QueueEntry getQueueEntry() {
        return rolster;
    }

    public long getWaitingTime() {
        return this.getQueueEntry().getWaitingTime();
    }
}
