package snakesAndLadder.utils;

import java.util.Random;

public class RandomGenerator {
    private static final Random RANDOM = new Random();
    public static Integer generateRandom(int min,int max){
        return min + RANDOM.nextInt(max-min+1);
    }
}
