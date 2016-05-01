package matchmaker.queue;

import com.ClientHandler;
import config.QueueConfig;
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

    public void update(){
        for (Queue q : queues
             ) {
            q.onUpdate();
        }
    }

    public static long generateId(long clientId,long queueCount){
        return clientId + 1000*queueCount;
    }

    public long createQueue(long clientId, QueueConfig config) {
        long queueId = -1;
        try {
            queueId = generateId(clientId, queueCount);
            ClientHandler.getClient(clientId).conf.addQueue("id_" + queueId, config);
            queues.add(new Queue(queueId, config));
            queueCount += 1;
        }catch(Exception e){
            System.out.printf(e.getMessage());
        }
        return queueId;
    }

    public long createQueue(long clientId, String queueKey) {
        long queueId = -1;
        try {
            queueId = generateId(clientId, queueCount);
            queues.add(new Queue(queueId, ClientHandler.getClient(clientId).conf.queueConfigs.get(queueKey)));
            queueCount += 1;
        }catch(Exception e){
            System.out.printf(e.getMessage());
        }
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
        update();
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                return q.getFoundMatches();
            }
        }
        return null;
    }
}
