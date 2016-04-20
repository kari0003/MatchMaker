package matchmaker.match;

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
        this.rolster = entry;
        this.teamPosition = teamPosition;
    }

    public double getScore(){
        return rolster.player.getElo();
    }

    public QueueEntry getQueueEntry() {
        return rolster;
    }
}
