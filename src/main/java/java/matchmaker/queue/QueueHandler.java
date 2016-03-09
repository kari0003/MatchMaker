package java.matchmaker.queue;

import java.util.HashSet;

/**
 * Created by Robi on 2016.02.26..
 * Responsible for updating, deleting, creating the queues (based on their principles).
 * calls the onUpdate method when the queue should be updated.
 */
public class QueueHandler {
    private HashSet<Queue> queues = new HashSet<>();

    public void update(){
        for (Queue q : queues
             ) {
            q.onUpdate();
        }
    }
}
