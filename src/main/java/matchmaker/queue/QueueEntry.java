package matchmaker.queue;

import matchmaker.match.Player;
import matchmaker.match.Team;

/**
 * Created by Robi on 2016.02.26..
 * An entry in the Queue. Can be passed to the QueueMatcher, which in case of a match transforms it into a TeamMember.
 */
public class QueueEntry {
    public Player player;
    private long waitingTime;

    public QueueEntry(Player player) {
        this.player = player;
        waitingTime = 0;
    }

    public double getDist(QueueEntry from, int waitModifier, int maxWaitModification){
        double plainDist = getDist(from);
        //Only the modifier of the entry that waited less will be considered.
        double waitModification = (Math.min(from.getWaitingTime(),waitingTime)/1000.0)*waitModifier;
        //The dist cant be less than 0, and cant be modified by more than what the config says.
        double distWithWait = Math.max(0, plainDist - Math.min(waitModification, maxWaitModification));
        return distWithWait;
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

    public long getWaitingTime() {
        return waitingTime;
    }

    public void addWaitingTime(long delta) {
        this.waitingTime += delta;
    }
}
