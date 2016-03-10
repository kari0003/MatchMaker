package matchmaker.match;

import matchmaker.Player;

/**
 * Created by Robi on 2016.02.26..
 * Represents a member of a team of a match. The team member has the useful information regards the player's position in the match
 */
public class TeamMember{
    private int teamPosition;
    private Player player;

    public TeamMember(int teamPosition, Player p){
        this.player = p;
        this.teamPosition = teamPosition;
    }
}
