package matchmaker.match;

import java.util.HashMap;

/**
 * Created by Robi on 2016.02.25..
 * Contains the basic informations about the player. The player enters a Queue as a super object of QueueMember, and
 * leaves the queue (in case of a match) as a super object of TeamMember.
 */
public class Player {
    private static int lastId = 0;
    public final int id;
    public final String name;
    private int elo;
    public HashMap<String, Integer> scores = new HashMap<>();

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

    public int getScore(String key){
        return scores.get(key);
    }

    public void setScore(String key, int value){
        scores.put(key,value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != player.id) return false;
        return name.equals(player.name);
    }
}
