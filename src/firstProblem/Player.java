package firstProblem;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Comparable{


    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private int point = 0;
    private PlayGround playGround;
    @Override
    public int compareTo(Object o) {
        return ((Player)o).point - this.point;
    }

    public void setPlayGround(PlayGround playGround) {
        this.playGround = playGround;
    }

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

    public static Player searchPlayer(String name){
        for (Player player : players) {
            if(player.getName().equals(name))
                return player;
        }
        return null;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
}
