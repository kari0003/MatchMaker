package matchmaker.queue;

import config.MatchConfig;
import config.MatcherConfig;
import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.match.TeamMember;

import java.util.LinkedList;

/**
 * Matches players based on a principle in the queue when asked.
 * Created by Robi on 2016.02.26..
 */
public class QueueMatcher {
    public MatchConfig matchConfig;
    public MatcherConfig matcherConfig;

    public QueueMatcher(int teamSize, int teamCount){
        this.matchConfig.teamSize = teamSize;
        this.matchConfig.teamCount = teamCount;
    }

    public QueueMatcher(MatcherConfig matcherConfig, MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
        this.matcherConfig = matcherConfig;
    }

    public LinkedList<Match> findMatches(LinkedList<QueueEntry> players){
        LinkedList<Match> matches = new LinkedList<Match>();
        int teamSize = matchConfig.teamSize;
        int teamCount = matchConfig.teamCount;
        while(players.size() > teamSize*teamCount){
            Match result = new Match(QueueHandler.generateMatchId(),teamCount,teamSize);
            for(int teamId = 0; teamId<teamCount; teamId++){
                for(int position = 0; position < teamSize; position++){
                    TeamMember member = new TeamMember(position , players.pop());
                    result.getTeam(teamId).addMember(member);
                }
            }
            matches.add(result);
        }
        if(matches.size() > 0){
            return matches;
        }
        return null;
    }
}
