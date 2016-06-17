package matchmaker.queue;

import config.MatchConfig;
import config.MatcherConfig;
import config.QueueConfig;
import matchmaker.match.Player;
import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.queue.matcher.EloMatcher;
import matchmaker.queue.matcher.RolsterMatcher;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.02.25..
 * Represents a queue, in witch the players are being matched. The queue is responsible for any operations in the entry list.
 * The way the queue operates are determined by QueueMatcher, and QueueHandler. The different types of queues can be
 * assembled with configuring these objects.
 */
public class Queue {
    private final long queueId;
    private long clientId;
    private QueueStatus status;
    private QueueConfig conf;
    private LinkedList<QueueEntry> players = new LinkedList<>();
    private QueueMatcher matcher;
    private LinkedList<Match> found_matches = new LinkedList<>();
    private long lastUpdateTime;

    public Queue(long id, long clientId, QueueConfig config) throws Exception {
        queueId = id;
        this.clientId = clientId;
        this.conf = config;
        status = QueueStatus.ACTIVE;
        lastUpdateTime = System.currentTimeMillis();
        matcher = createMatcher(config);
    }

    private QueueMatcher createMatcher(QueueConfig config) throws Exception {
        if(config.matcherConfig == null) {
            throw new Exception("Matcher Configuration not found!");
        }
        if(config.matchConfig == null) {
            throw new Exception("Match Configuration not found!");
        }
        switch (config.matcherConfig.matcherType) {
            case ELO_MATCHER:
                return new EloMatcher(config.matcherConfig, config.matchConfig);
            case ROLSTER_MATCHER:
                return new RolsterMatcher(config.matcherConfig, config.matchConfig);
            default:
                return new QueueMatcher(config.matcherConfig, config.matchConfig);

        }
    }

    public QueueStatus onUpdate() {
        LinkedList<Match> matches = matcher.findMatches(players);
        long currentTime = System.currentTimeMillis();
        long delta = getTimeElapsed(currentTime);
        for(QueueEntry entry : getEntries()){
            entry.addWaitingTime(delta);
        }
        if (matches != null && matches.size() > 0) {
            status = QueueStatus.MATCH_FOUND;
            Queue.draftRolsters(matches);
            found_matches.addAll(matches);
        }
        lastUpdateTime = currentTime;
        return status;
    }

    private static void draftRolsters(LinkedList<Match> matches) {
        for(Match m : matches){
            for(int teamId = 0; teamId < m.getTeamCount(); teamId++){
                Team t = m.getTeam(teamId);
                for(QueueEntry rolster : t.getQueueEntries()){
                    rolster.drafted = true;
                }
            }
        }
    }

    private long getTimeElapsed(long currentTime) {
        long delta = currentTime - lastUpdateTime;
        return delta;
    }

    public void addPlayer(Player player) {
        players.add(new QueueEntry(player));
        if(conf.updateOnInsert){
            onUpdate();
        }
    }

    public void removePlayer(Player player) {
        for (QueueEntry qe : players) {
            if(qe.player.equals(player)){
                players.remove(qe);
            }
        }
    }

    public long getQueueId(){
        return queueId;
    }

    public QueueStatus getStatus(){
        return status;
    }

    public void setStatus(QueueStatus status){
        this.status = status;
    }

    public LinkedList<Match> getFoundMatches(){
        return found_matches;
    }

    public LinkedList<QueueEntry> getEntries() {
        return players;
    }

    public long getClient() {
        return clientId;
    }

    public void setClient(long clientId) {
        this.clientId = clientId;
    }

    public QueueConfig getConfig() {
        return conf;
    }
}
