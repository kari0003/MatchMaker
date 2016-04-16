package matchmaker.queue.matcher;

import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.queue.QueueEntry;
import matchmaker.queue.QueueMatcher;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.04.15..
 */
public class RolsterMatcher extends QueueMatcher {
    private int maxPotentials = 30;
    private double maxDist = 100;


    public RolsterMatcher(int teamSize, int teamCount) {
        super(teamSize, teamCount);
    }

    @Override
    public LinkedList<Match> findMatches(LinkedList<QueueEntry> rolsters){
        LinkedList<Match> results = new LinkedList<Match>();
        results.add(populateMatch(rolsters));
        return results;
    }

    private LinkedList<QueueEntry> getPotentials(LinkedList<QueueEntry> rolsters, QueueEntry target){
        LinkedList<QueueEntry> potentials = new LinkedList<QueueEntry>();
        for (QueueEntry rolster : rolsters) {
            if(target.getDist(rolster) < maxDist){
                potentials.push(rolster);
            }
            if(potentials.size() > maxPotentials){
                break;
            }
        }
        return potentials;
    }

    private int getTeamToInsertInto(Match match){
        int teamId = 0, minCount = 0;
        for(int i = 0; i<match.getTeamCount() ; i++){
            if((i == 0) || (minCount > match.getTeam(i).getMemberCount()) ){
                teamId = i;
                minCount = match.getTeam(i).getMemberCount();
            }
        }
        return teamId;
    }

    private QueueEntry pickRolster(Match forMatch, int forTeam, LinkedList<QueueEntry> potentials ){
        QueueEntry best = null;
        double bestDist = 0;
        for(QueueEntry rolster : potentials){
            if(rolster.getMemberCount() + forMatch.getTeam(forTeam).getMemberCount() < matchConfig.teamSize){
                if ((rolster.getDist(forMatch.getTeam(forTeam)) < bestDist )||(potentials.get(0) == rolster)){
                    bestDist = rolster.getDist(forMatch.getTeam(forTeam));
                    best = rolster;
                }
            }
        }
        return best;
    }

    private Match populateMatch(LinkedList<QueueEntry> rolsters){
        Match match = new Match(0, matchConfig.teamCount);
        for(int i = 0; i < matchConfig.teamCount; i++){
            match.addTeam(new Team(i, matchConfig.teamSize));
        }
        boolean success = false;
        boolean finished = false;
        QueueEntry target = rolsters.pop();
        LinkedList<QueueEntry> potentials = getPotentials(rolsters, target);
        while(!finished){
            int nextInsert = getTeamToInsertInto(match);

            QueueEntry found = pickRolster(match, nextInsert, potentials);

            potentials.remove(found);
            match.getTeam(nextInsert).addMember(found);

            if(potentials.size() == 0) {
                finished = true;
                if (false){//TODO match isn't full) {
                    success = false;
                }
            }
        }

        if(success) {
            return match;
        }else{
            return null;
        }
    }

}
