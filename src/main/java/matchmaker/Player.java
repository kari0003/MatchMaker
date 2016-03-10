package matchmaker;

/**
 * Created by Robi on 2016.02.25..
 * Contains the basic informations about the player. The player enters a Queue as a super object of QueueMember, and
 * leaves the queue (in case of a match) as a super object of TeamMember.
 */
public class Player {
    private int id;
    private String name;
    private int elo;
}
