package matchmaker.queue;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.03.17..
 * Handles events, and updates queues based on them lol.
 */
public class QueueEvents implements Runnable{
    private static LinkedList<Long> activeQueues = new LinkedList<Long>();

    @Override
    public void run() {
        try {
            while(true){
                Thread.sleep(1000);
                for(int i = 0; i < activeQueues.size(); i++){
                    QueueStatus status = QueueHandler.getHandler().update(activeQueues.get(i));
                    if (status == QueueStatus.INACTIVE){
                        activeQueues.remove(i);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void registerQueue(long queueId){
        activeQueues.add(queueId);
    }

    public static void unregisterQueue(long queueId){
        activeQueues.remove(queueId);
    }
}
