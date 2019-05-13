package firstProblem;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private int point = 0;
     public Player(String name){
         this.name = name;
         players.add(this);
     }
}
