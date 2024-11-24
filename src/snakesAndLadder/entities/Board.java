package snakesAndLadder.entities;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Integer,Integer> snakesAndLadders;
    public Board(HashMap<Integer,Integer> snakesAndLadders){
        this.snakesAndLadders = snakesAndLadders;
    }

    public Map<Integer, Integer> getSnakesAndLadders() {
        return snakesAndLadders;
    }
}
