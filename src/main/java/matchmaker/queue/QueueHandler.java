package matchmaker.queue;

import com.Client;
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
            queues.add(new Queue(queueId, clientId, config));
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
            Client client = ClientHandler.getClient(clientId);
            System.out.printf("WE ARE SCREWED");
            if(client == null){
                throw new Error("Client with id:" + clientId + "not found!");
            }
            System.out.printf("this is a queue creation:" + queueId + ". Also " + client.clientId);
            queues.add(new Queue(queueId, clientId, client.conf.queueConfigs.get(queueKey)));
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
                System.out.println("Found the queue!");
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

    public LinkedList<Queue> getClientQueues(long clientId) {
        LinkedList<Queue> clientQueues = new LinkedList<>();
        System.out.printf("Searchin for queues...");
        for(Queue q : queues){
            System.out.printf("queue: " +q.getClient());
            System.out.printf("input: " +clientId);
            if(q.getClient() == clientId){
                System.out.println("Found one!");
                clientQueues.push(q);
            }
        }
        return clientQueues;
    }

    public void deleteQueue(long queueId) {
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                queues.remove(q);
                break;
            }
        }
    }

    public void startQueue(long queueId) {
        for(Queue q : queues){
            if(queueId == q.getQueueId()){
                q.setStatus(QueueStatus.ACTIVE);
                final Queue qe = q;
                Thread queueThread = new Thread(){
                    @Override
                    public void run()
                    {
                        while(true) {
                            qe.onUpdate();
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
        }
    }

    private static int lastMatchId = -1;
    public static int generateMatchId() {
        lastMatchId++;
        return lastMatchId;
    }
}
