package matchmaker.queue;

import com.configs.QueueConfig;
import matchmaker.match.Match;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Robi on 2016.02.26..
 * Responsible for updating, deleting, creating the queues (based on their principles).
 * calls the onUpdate method when the queue should be updated.
 */
public class QueueHandler {
    private static QueueHandler instance;
    private HashSet<Queue> queues = new HashSet<>();
    private long queueCount;

    public QueueHandler(){
        queueCount = 0;
    }

    public QueueStatus update(long queueId){
        for (Queue q : queues
             ) {
            if(queueId == q.getQueueId()){
                return q.onUpdate();
            }
        }
        return null;
    }

    public long createQueue(long clientId, QueueConfig config) {
        long queueId = clientId + 1000*queueCount;
        queues.add(new Queue(clientId + 1000*queueCount, config));
        QueueEvents.registerQueue(queueId);
        return queueId;
    }

    public static QueueHandler getHandler() {
        return instance;
    }

    public static void initialize() {
        instance = new QueueHandler();
    }

    public Queue getQueue(long queueId) {
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                return q;
            }
        }
        return null;
    }

    public LinkedList<Match> checkQueue(long queueId) {
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                return q.getFoundMatches();
            }
        }
        return null;
    }
}
