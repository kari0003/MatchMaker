package matchmaker.queue;

import com.Client;
import com.ClientHandler;
import config.QueueConfig;
import matchmaker.match.Match;

import java.util.HashMap;
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
    private HashMap<Long,Thread> updaterThread = new HashMap<>();
    private long queueCount;
    private static int lastMatchId = -1;

    public QueueHandler(){
        queueCount = 0;
    }

    public static QueueHandler getHandler() {
        return instance;
    }

    public static void initialize() {
        instance = new QueueHandler();
    }

    public void updateQueue(long queueId){
        for (Queue q : queues) {
            if(q.getQueueId() == queueId) {
                q.onUpdate();
            }
        }
    }

    public Thread createUpdaterThread(final long queueId, final QueueConfig conf){
        Thread updater = new Thread(){
            public volatile boolean shouldInterrupt = false;

            @Override
            public void run() {
                while(!shouldInterrupt) {
                    try {
                        updateQueue(queueId);
                        Thread.sleep(conf.updateInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(shouldInterrupt && isAlive()){
                    this.interrupt();
                }
            }
        };
        updaterThread.put(queueId, updater);
        return updater;
    }

    public long createQueue(long clientId, QueueConfig config) {
        long queueId = -1;
        try {
            queueId = generateId(clientId, queueCount);
            Client client = ClientHandler.getClient(clientId);
            if(client == null){
                System.out.println("ClIENT IS NOULL");
                throw new Error("Client with id:" + clientId + "not found!");
            }
            if(queues == null){
                System.out.println("WTF? QUEUES IS NULL");
            }
            if(config == null) {
                System.out.println("FRIGGIN CONFIG NULL -.- _:_");
            }
            queues.add(new Queue(queueId, clientId, config));
            createUpdaterThread(queueId, config);
            queueCount += 1;
        }catch(Exception e){
            System.out.printf(e.getMessage());
        }
        return queueId;
    }

    public long createQueue(long clientId, String queueKey) {
        Client client = ClientHandler.getClient(clientId);
        if(client == null){
            throw new Error("Client with id: " + clientId + " not found!");
        }
        QueueConfig config = client.conf.queueConfigs.get(queueKey);
        if (config == null) {
            throw new Error("Config with key: " + queueKey + " not found");
        }
        return createQueue(clientId, config);
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
                System.out.printf("ChekcQueue");
                if(q.getConfig().updateWhenChecked){
                    updateQueue(queueId);
                }
                return q.getFoundMatches();
            }
        }
        return null;
    }

    public LinkedList<Queue> getClientQueues(long clientId) {
        LinkedList<Queue> clientQueues = new LinkedList<>();
        for(Queue q : queues){
            System.out.printf("queue: " +q.getClient());
            System.out.printf("input: " +clientId);
            if(q.getClient() == clientId){
                clientQueues.push(q);
            }
        }
        return clientQueues;
    }

    public void deleteQueue(long queueId) {
        Queue removableQueue = null;
        Thread removableThread = updaterThread.remove(queueId);
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                removableQueue = q;
                break;
            }
        }
        queues.remove(removableQueue);
        //TODO removableThread.shouldInterrupt = true;

    }

    public void startQueue(long queueId) {
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                q.setStatus(QueueStatus.ACTIVE);
                updaterThread.get(queueId).start();
                break;
            }
        }
    }

    public static int generateMatchId() {
        lastMatchId++;
        return lastMatchId;
    }

    public static long generateId(long clientId,long queueCount){
        return 1000*clientId + queueCount;
    }

}
