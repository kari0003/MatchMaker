package matchmaker.queue.matcher;

import com.ClientHandler;
import config.*;
import matchmaker.match.Match;
import matchmaker.match.Player;
import matchmaker.queue.Queue;
import matchmaker.queue.QueueEntry;
import matchmaker.queue.QueueHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Robi on 2016.05.16..
 */
public class QueueWaitTest {
    long queueId;
    QueueConfig conf;


    @Before
    public void setUp() throws Exception {
        QueueHandler.initialize();
        ClientHandler.initialize();
        long cId = ClientHandler.createClient(new ClientConfig());
        MatchConfig matchConfig = new MatchConfig(2,3);
        MatcherConfig matcherConfig = new MatcherConfig(MatcherType.ELO_MATCHER, 30,1, 100, 300, 10, 30, 10, 100);
        matcherConfig.considerElo(1.0);
        conf = new QueueConfig(100, matcherConfig, matchConfig);
        conf.updateOnInsert = false;
        conf.updateWhenChecked = false;
        queueId = QueueHandler.getHandler().createQueue(cId, conf);
        QueueHandler.getHandler().startQueue(queueId);
    }

    @Test
    public void testFindMatchesPerfectMatch() throws Exception {
        LinkedList<Player> players = new LinkedList<>();
        players.push(new Player("Podric", 1300));
        players.push(new Player("Pongrác", 1300));
        players.push(new Player("Persival", 1300));
        players.push(new Player("Pondró", 1300));
        players.push(new Player("Plakó", 1300));
        players.push(new Player("Korsó", 1300));
        players.push(new Player("Porhó", 1300));
        for(Player p : players){
            QueueHandler.getHandler().getQueue(queueId).addPlayer(p);
        }
        Thread.sleep(conf.updateInterval + 10);
        LinkedList<Match> matches = QueueHandler.getHandler().checkQueue(queueId);
        assertNotNull(matches);
        //assertEquals(2, matches.size());
        assertEquals(3 , matches.getFirst().getTeamCount());
        for(int i = 0; i < matches.getFirst().getTeamCount(); i++){
            assertEquals(2 , matches.getFirst().getTeam(i).getMemberCount());
        }
        assertTrue(300 >= matches.getFirst().getMaxDistance());

    }


}
