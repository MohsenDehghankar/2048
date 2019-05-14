package firstProblem;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Comparable{
    @Override
    public int compareTo(Object o) {
        return ((Player)o).point - this.point;
    }

    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private int point = 0;

    public Player(String name) {
        this.name = name;
        players.add(this);
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Player> getPlayers() {
        Collections.sort(players);
        return players;
    }

    public void addPoint(int point){
        this.point += point;
    }

    public int getPoint() {
        return point;
    }


}
