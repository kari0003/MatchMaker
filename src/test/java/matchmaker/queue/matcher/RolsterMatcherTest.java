package matchmaker.queue.matcher;

import config.MatchConfig;
import config.MatcherConfig;
import config.MatcherType;
import matchmaker.match.Match;
import matchmaker.match.Player;
import matchmaker.queue.QueueEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by Robi on 2016.04.30..
 */
public class RolsterMatcherTest {

    RolsterMatcher matcher;

    @Before
    public void setUp() throws Exception {
        MatchConfig matchConfig = new MatchConfig(2,3);
        MatcherConfig matcherConfig = new MatcherConfig(MatcherType.ROLSTER_MATCHER, 30,1, 100, 300);
        matcherConfig.considerElo(1.0);
        matcher = new RolsterMatcher(matcherConfig, matchConfig);
    }

    @Test
    public void testFindMatchesPerfectMatch() throws Exception {
        LinkedList<QueueEntry> rolsters = new LinkedList<>();
        rolsters.push(new QueueEntry(new Player("Podric", 1300)));
        rolsters.push(new QueueEntry(new Player("Pongrác", 1300)));
        rolsters.push(new QueueEntry(new Player("Persival", 1300)));
        rolsters.push(new QueueEntry(new Player("Pondró", 1300)));
        rolsters.push(new QueueEntry(new Player("Podric1", 1300)));
        rolsters.push(new QueueEntry(new Player("Pongrác1", 1300)));
        rolsters.push(new QueueEntry(new Player("Persival1", 1300)));
        rolsters.push(new QueueEntry(new Player("Pondró1", 1300)));
        LinkedList<Match> matches = matcher.findMatches(rolsters);
        assertNotNull(matches);
        assertEquals(matcher.matchConfig.teamCount, matches.getFirst().getTeamCount());
        for(int i = 0; i < matcher.matchConfig.teamCount; i++){
            assertEquals(matcher.matchConfig.teamSize, matches.getFirst().getTeam(i).getMemberCount());
        }
        assertTrue(matcher.matcherConfig.maxDistanceTeams >= matches.getFirst().getMaxDistance());

    }


    @Test
    public void testFindMatchesTeamDistance() throws Exception {
        LinkedList<QueueEntry> rolsters = new LinkedList<>();
        rolsters.push(new QueueEntry(new Player("Podric", 1300)));
        rolsters.push(new QueueEntry(new Player("Pongrác", 1400)));
        rolsters.push(new QueueEntry(new Player("Persival", 1500)));
        rolsters.push(new QueueEntry(new Player("Pondró", 1600)));
        rolsters.push(new QueueEntry(new Player("Podric1", 1700)));
        rolsters.push(new QueueEntry(new Player("Pongrác1", 1800)));
        rolsters.push(new QueueEntry(new Player("Persival1", 1900)));
        rolsters.push(new QueueEntry(new Player("Pondró1", 2000)));
        LinkedList<Match> matches = matcher.findMatches(rolsters);
        assertNotNull(matches);
        assertEquals(0, matches.size());
    }
    @Test
    public void testFindMatchesRolsterDistance() throws Exception {
        LinkedList<QueueEntry> rolsters = new LinkedList<>();
        rolsters.push(new QueueEntry(new Player("Podric", 1300)));
        rolsters.push(new QueueEntry(new Player("Pongrác", 1401)));
        rolsters.push(new QueueEntry(new Player("Persival", 1502)));
        rolsters.push(new QueueEntry(new Player("Pondró", 1603)));
        rolsters.push(new QueueEntry(new Player("Podric1", 1704)));
        rolsters.push(new QueueEntry(new Player("Pongrác1", 1805)));
        rolsters.push(new QueueEntry(new Player("Persival1", 1906)));
        rolsters.push(new QueueEntry(new Player("Pondró1", 2007)));
        LinkedList<Match> matches = matcher.findMatches(rolsters);
        assertNotNull(matches);
        assertEquals(0, matches.size());
    }
}