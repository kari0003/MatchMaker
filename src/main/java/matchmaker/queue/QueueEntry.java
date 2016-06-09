package matchmaker.queue;

import config.MatcherConfig;
import matchmaker.match.Player;
import matchmaker.match.Team;

/**
 * Created by Robi on 2016.02.26..
 * An entry in the Queue. Can be passed to the QueueMatcher, which in case of a match transforms it into a TeamMember.
 */
public class QueueEntry {
    public Boolean drafted; //for a match
    public Player player;
    private long waitingTime;

    public QueueEntry(Player player) {
        this.player = player;
        waitingTime = 0;
        drafted = false;
    }

    public double getDist(QueueEntry from, MatcherConfig matcher){
        double dist = 0;
        for(String key : matcher.aspectNames){
            if(matcher.considerAspect.get(key)){
                dist += matcher.weighAspect.get(key) * getDist(from, key);
            }
        }
        if(matcher.considerWait) {
            //Only the modifier of the entry that waited less will be considered.
            double waitModification = (Math.min(from.getWaitingTime(), waitingTime) / 1000.0) * matcher.waitModifier;
            //The dist cant be less than 0, and cant be modified by more than what the config says.
            double distWithWait = Math.max(0, dist - Math.min(waitModification, matcher.maxWaitModification));
            dist = distWithWait;
        }
        return dist;
    }

    public double getDist(QueueEntry from, String aspect){
        return Math.abs(from.player.getScore(aspect) - player.getScore(aspect));
    }

    public double getDist(Team team) {
        return 0;//TODO
    }

    public int getMemberCount(){
        return 1;
    }

    public double getScore(String key){
        return player.getScore(key);
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void addWaitingTime(long delta) {
        this.waitingTime += delta;
    }
}
