package matchmaker.queue.matcher;

import config.MatcherConfig;
import matchmaker.match.Match;
import matchmaker.match.Team;
import matchmaker.queue.QueueEntry;
import matchmaker.queue.QueueMatcher;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.04.15..
 */
public class RolsterMatcher extends QueueMatcher {
    private int maxTargets = 1;
    private int maxPotentials = 30;
    private double maxDistancePlayers = 100;
    private double maxDistanceTeams = 300;


    public RolsterMatcher(MatcherConfig conf, int teamSize, int teamCount)
    {
        super(teamSize, teamCount);
        maxTargets = conf.maxTargets;
        maxPotentials = conf.maxPotentials;
        maxDistancePlayers = conf.maxDistancePlayers;
        maxDistanceTeams = conf.maxDistanceTeams;
    }

    @Override
    public LinkedList<Match> findMatches(LinkedList<QueueEntry> rolsters){
        LinkedList<Match> results = new LinkedList<>();
        LinkedList<QueueEntry> targets = gatherTargets(rolsters);
        while(targets.size() > 0){
            Match selected = populateMatch(targets.pop(), rolsters);
            if(selected != null){
                results.add(selected);
                rolsters.removeAll(selected.getQueueEntries());
            }
        }
        return results;
    }

    private LinkedList<QueueEntry> gatherTargets(LinkedList<QueueEntry> rolsters) {
        LinkedList<QueueEntry> result = new LinkedList<>();
        int count = Math.min(maxTargets, rolsters.size());
        for(int i = 0; i< maxTargets; i++){
            result.add(rolsters.get(i));
        }
        //TODO configurable target selection
        //result.add(rolsters.getLast());
        return result;
    }

    private LinkedList<QueueEntry> getPotentials(LinkedList<QueueEntry> rolsters, QueueEntry target){
        LinkedList<QueueEntry> potentials = new LinkedList<QueueEntry>();
        for (QueueEntry rolster : rolsters) {
            if(target.player != rolster.player) {
                if (target.getDist(rolster) < maxDistancePlayers) {
                    potentials.push(rolster);
                }
                if (potentials.size() > maxPotentials) {
                    break;
                }
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

    private Match populateMatch(QueueEntry target, LinkedList<QueueEntry> rolsters){
        Match match = new Match(0, matchConfig.teamCount);
        for(int i = 0; i < matchConfig.teamCount; i++){
            match.addTeam(new Team(i, matchConfig.teamSize));
        }
        boolean success = false;
        boolean finished = false;
        LinkedList<QueueEntry> potentials = getPotentials(rolsters, target);
        while(!finished){
            int nextInsert = getTeamToInsertInto(match);

            QueueEntry found = pickRolster(match, nextInsert, potentials);

            potentials.remove(found);
            match.getTeam(nextInsert).addMember(found);

            if(potentials.size() == 0) {
                finished = true;
                success = validateMatch(match);
            }

            if(validateMatch(match)){
                finished = true;
                success = true;
            }
        }

        if(success) {
            return match;
        }else{
            return null;
        }
    }

    private boolean validateMatch(Match match) {
        if(match.getTeamCount() != matchConfig.teamCount){
            return false;
        }
        if (match.getMaxDistance() > maxDistanceTeams){
            return false;
        }
        for(int i = 0; i < match.getTeamCount(); i++){
            if(match.getTeam(i).getTeamDist() > maxDistancePlayers){
                return false;
            }
            if(match.getTeam(i).getMemberCount() != matchConfig.teamSize){
                return false;
            }
        }
        return true;
    }

}
