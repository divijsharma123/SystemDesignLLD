package snakesAndLadder.service;

import snakesAndLadder.config.Constants;
import snakesAndLadder.utils.RandomGenerator;

public class DiceService {
    private static DiceService INSTANCE;

    private DiceService(){

    }

    public static DiceService getInstance() {
        if(INSTANCE==null){
            synchronized (DiceService.class){
                if(INSTANCE==null){
                    INSTANCE = new DiceService();
                }
            }
        }
        return INSTANCE;
    }
    public Integer rollDice(){
        return RandomGenerator.generateRandom(Constants.minDiceValue(),Constants.getMaxDiceValue());
    }
}
