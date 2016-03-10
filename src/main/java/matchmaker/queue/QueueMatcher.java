package matchmaker.queue;

import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.match.TeamMember;

import java.util.LinkedList;

/**
 * Matches players based on a principle in the queue when asked.
 * Created by Robi on 2016.02.26..
 */
public class QueueMatcher {
    private int teamSize;
    private int teamCount;

    public QueueMatcher(int teamSize, int teamCount){
        this.teamSize = teamSize;
        this.teamCount = teamCount;
    }

    public LinkedList<Match> findMatches(LinkedList<QueueEntry> players){
        LinkedList<Match> matches = new LinkedList<Match>();
        while(players.size() > teamSize*teamCount){
            Match result = new Match(0); //TODO Match id generation
            for(int teamId = 0; teamId<teamCount; teamId++){
                Team iteraTeam = new Team(teamCount);
                for(int position = 0; position < teamSize; position++){
                    TeamMember member = new TeamMember(position , players.pop().player);
                    iteraTeam.addMember(member);
                }
                result.addTeam(iteraTeam);
            }
            matches.add(result);
        }
        if(matches.size() > 0){
            return matches;
        }
        return null;
    }
}
