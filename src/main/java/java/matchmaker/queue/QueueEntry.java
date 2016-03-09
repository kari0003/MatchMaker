package java.matchmaker.queue;

import matchmaker.Player;

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
}
