package matchmaker.queue;

import config.QueueConfig;
import matchmaker.match.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by Robi on 2016.04.30..
 */
public class QueueTest {
    Queue queue;

    @Before
    public void setUp() throws Exception{
        queue = new Queue(0,new QueueConfig());
    }

    @Test
    public void testOnUpdate() throws Exception {
        queue.onUpdate();
        assertEquals(QueueStatus.ACTIVE, queue.getStatus());
    }

    @Test
    public void testAddPlayer() throws Exception {
        Player player = new Player("Test Player", 1300);
        queue.addPlayer(player);
        assertEquals(queue.getEntries().getFirst().player, player);
    }

    @Test
    public void testRemovePlayer() throws Exception {
        Player player = new Player("Test Player", 1300);
        queue.addPlayer(player);
        assertEquals(player, queue.getEntries().getFirst().player);
        queue.removePlayer(player);
        assertEquals(new LinkedList<QueueEntry>(), queue.getEntries());
    }
}