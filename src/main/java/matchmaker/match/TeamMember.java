package matchmaker.match;

import matchmaker.Player;

import java.io.Serializable;

/**
 * Created by Robi on 2016.02.26..
 * Represents a member of a team of a match. The team member has the useful information regards the player's position in the match
 */
public class TeamMember implements Serializable{
    public final int teamPosition;
    public final Player player;

    public TeamMember(int teamPosition, Player p){
        this.player = p;
        this.teamPosition = teamPosition;
    }

    public TeamMember(){
        teamPosition = -1;
        player = new Player();
    }
}
