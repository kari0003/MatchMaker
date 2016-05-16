package matchmaker.queue.matcher;

import config.MatchConfig;
import config.MatcherConfig;
import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.match.TeamMember;
import matchmaker.queue.QueueEntry;
import matchmaker.queue.QueueHandler;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Matches players based on elo distance, lol.
 * Created by Robi on 2016.02.26..
 */
public class EloMatcher {
    public MatchConfig matchConfig;
    public MatcherConfig matcherConfig;

    public EloMatcher(MatcherConfig matcherConfig, MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
        this.matcherConfig = matcherConfig;
    }

    //TODO // FIXME: 2016.04.30. 
    public LinkedList<Match> findMatches(LinkedList<QueueEntry> rolsters){
        LinkedList<Match> matches = new LinkedList<Match>();

        int teamSize = matchConfig.teamSize;
        int teamCount = matchConfig.teamCount;

        QueueEntry picked = pickRolster(rolsters);
        LinkedList<QueueEntry> scope = getPotentials(picked, rolsters);
        if(scope.size() > teamSize*teamCount){
            Match match = populateMatch(picked, scope);
            if(match != null) {
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

        Comparator<QueueEntry> comparator = new Comparator<QueueEntry>() {
            public int compare(QueueEntry c1, QueueEntry c2) {
                return (int)(c2.getDist(picked) - c1.getDist(picked));
            }
        };
        Collections.sort(scope, comparator);

        Match result = new Match(QueueHandler.generateMatchId(), teamCount,teamSize);
        for(int teamId = 0; teamId<teamCount; teamId++){
            result.addTeam(new Team(teamId, teamSize));
        }
        for(int position = 0; position < teamSize; position++){
            for(int teamId = 0; teamId<teamCount; teamId++) {
                TeamMember member = new TeamMember(position, scope.pop());
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
