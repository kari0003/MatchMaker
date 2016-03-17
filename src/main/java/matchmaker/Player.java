package matchmaker;

/**
 * Created by Robi on 2016.02.25..
 * Contains the basic informations about the player. The player enters a Queue as a super object of QueueMember, and
 * leaves the queue (in case of a match) as a super object of TeamMember.
 */
public class Player {
    private static int lastId = 0;
    public final int id;
    public final String name;
    public final int elo;

    public Player(String name,int elo) {
        lastId += 1;
        id = lastId;
        this.name = name;
        this.elo = elo;
    }

    public Player() {
        id = -1;
        name = "Dummy";
        elo = 0;
    }
}
