package matchmaker.queue.matcher;

import config.MatchConfig;
import config.MatcherConfig;
import config.MatcherType;
import org.junit.Before;

/**
 * Created by Robi on 2016.05.16..
 */
public class QueueWaitTest {
    EloMatcher matcher;


    @Before
    public void setUp() throws Exception {
        MatchConfig matchConfig = new MatchConfig(2,3);
        MatcherConfig matcherConfig = new MatcherConfig(MatcherType.ROLSTER_MATCHER, 30,1, 100, 300, 10, 30, 10, 100);
        matcherConfig.considerElo(1.0);
        matcher = new EloMatcher(matcherConfig, matchConfig);
    }

}
