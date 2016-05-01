package matchmaker.queue;

import com.ClientHandler;
import config.ClientConfig;
import config.QueueConfig;
import matchmaker.match.Match;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by Robi on 2016.04.30..
 */
public class QueueHandlerTest {
    long clientId;
    long queueId;

    @Before
    public void setUp() throws Exception {
        ClientHandler.initialize();
        clientId = ClientHandler.createClient(new ClientConfig());
        QueueHandler.initialize();
        queueId = QueueHandler.getHandler().createQueue(clientId, "test");
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testCreateQueue() throws Exception {
        long queueId = QueueHandler.getHandler().createQueue(clientId, "test");
        assertNotEquals(-1, queueId);
        assertEquals(QueueHandler.generateId(clientId, 1), queueId);
    }

    @Test
    public void testCreateQueue1() throws Exception {
        long queueId = QueueHandler.getHandler().createQueue(clientId, new QueueConfig());
        assertNotEquals(-1, queueId);
        assertEquals( QueueHandler.generateId(clientId, 1), queueId);
    }

    @Test
    public void testGetQueue() throws Exception {
        Queue q = QueueHandler.getHandler().getQueue(queueId);
        assertNotNull(q);
        assertEquals(q.getEntries(), new LinkedList<QueueEntry>());
    }

    @Test
    public void testCheckQueue() throws Exception {
        LinkedList<Match> match = QueueHandler.getHandler().checkQueue(queueId);
        assertNotNull(match);
    }
}