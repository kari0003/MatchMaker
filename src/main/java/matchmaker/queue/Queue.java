package matchmaker.queue;

import matchmaker.Player;
import matchmaker.match.Match;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.02.25..
 * Represents a queue, in witch the players are being matched. The queue is responsible for any operations in the entry list.
 * The way the queue operates are determined by QueueMatcher, and QueueHandler. The different types of queues can be
 * assembled with configuring these objects.
 */
public class Queue {
    private QueueStatus status;
    private LinkedList<QueueEntry> players = new LinkedList<>();
    private QueueMatcher matcher;
    private LinkedList<Match> found_matches = new LinkedList<>();

    public QueueStatus onUpdate() {
        LinkedList<Match> matches = matcher.findMatches(players);
        if (matches != null && matches.size() > 0) {
            status = QueueStatus.MATCH_FOUND;
            found_matches.addAll(matches);
        }
        return status;
    }

    public void addPlayer(Player player) {
        players.add(new QueueEntry(player));
    }

    public void removePlayer(Player player) {
        //players.remove();
    }


}
