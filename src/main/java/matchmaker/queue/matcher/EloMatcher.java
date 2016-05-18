package matchmaker.queue.matcher;

import config.MatchConfig;
import config.MatcherConfig;
import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.match.TeamMember;
import matchmaker.queue.QueueEntry;
import matchmaker.queue.QueueHandler;
import matchmaker.queue.QueueMatcher;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Matches players based on elo distance, lol.
 * Created by Robi on 2016.02.26..
 */
public class EloMatcher extends QueueMatcher {
    public MatchConfig matchConfig;
    public MatcherConfig matcherConfig;

    public EloMatcher(MatcherConfig matcherConfig, MatchConfig matchConfig) {
        super(matcherConfig, matchConfig);
        this.matchConfig = matchConfig;
        this.matcherConfig = matcherConfig;
    }

    public LinkedList<Match> findMatches(LinkedList<QueueEntry> rolsters){
        LinkedList<Match> matches = new LinkedList<Match>();

        int teamSize = matchConfig.teamSize;
        int teamCount = matchConfig.teamCount;

        if(rolsters.size() == 0){
            return null;
        }
        QueueEntry picked = pickRolster(rolsters);
        LinkedList<QueueEntry> scope = getPotentials(picked, rolsters);
        if(scope.size() > teamSize*teamCount){
            System.out.println("teamSize*teamCount = " + teamSize*teamCount);
            Match match = populateMatch(picked, scope);
            if(match != null) {
                System.out.println("match = " + match);
                matches.add(match);
            }
        }
        if(matches.size() > 0){
            return matches;
        }
        return null;
    }

    private Match populateMatch(final QueueEntry picked, LinkedList<QueueEntry> scope) {
        int teamSize = matchConfig.teamSize;
        int teamCount = matchConfig.teamCount;
        System.out.println("llelleleleeleleleleleleel");
        Comparator<QueueEntry> comparator = new Comparator<QueueEntry>() {
            public int compare(QueueEntry c1, QueueEntry c2) {
                return (int)(c2.getDist(picked) - c1.getDist(picked));
            }
        };
        Collections.sort(scope, comparator);

        System.out.println("Can you believe this");
        Match result = new Match(QueueHandler.generateMatchId(), teamCount,teamSize);

        for(int position = 0; position < teamSize; position++){
            for(int teamId = 0; teamId < teamCount; teamId++) {
                System.out.println("teamId = " + teamId);
                System.out.println("position = " + position);
                TeamMember member = new TeamMember(position, scope.pop());
                System.out.println("member = " + member);
                result.getTeam(teamId).addMember(member);
            }
        }
        return result;
    }

    private LinkedList<QueueEntry> getPotentials(QueueEntry picked, LinkedList<QueueEntry> rolsters) {
        LinkedList<QueueEntry> potentials = new LinkedList<>();
        for(QueueEntry rolster : rolsters){
            double dist = picked.getDist(rolster, matcherConfig.waitModifier, matcherConfig.maxWaitModification);
            if(dist < matcherConfig.maxDistancePlayers){
                potentials.add(rolster);
            }
        }
        return potentials;
    }

    private QueueEntry pickRolster(LinkedList<QueueEntry> rolsters) {
        return rolsters.getFirst();
    }
}
