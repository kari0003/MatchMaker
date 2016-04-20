package matchmaker.queue;

import matchmaker.match.Player;
import matchmaker.match.Team;

/**
 * Created by Robi on 2016.02.26..
 * An entry in the Queue. Can be passed to the QueueMatcher, which in case of a match transforms it into a TeamMember.
 */
public class QueueEntry {
    public Player player;
    public long enter_time;

    public QueueEntry(Player player) {
        this.player = player;
        enter_time = System.currentTimeMillis();
    }

    public double getDist(QueueEntry from){
        return Math.abs(from.player.getElo() - player.getElo());

    }

    public double getDist(Team team) {
        return 0;//TODO
    }

    public int getMemberCount(){
        return 1;
    }

    public double getScore() {
        return player.getElo();
    }
}
