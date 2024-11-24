package snakesAndLadder.entities;

import snakesAndLadder.config.Constants;

public class Ladder {
    private static Ladder INSTANCE;

    private Ladder(){

    }

    public static Ladder getInstance(){
        if(INSTANCE==null){
            synchronized (Ladder.class){
                if(INSTANCE==null){
                    INSTANCE = new Ladder();
                }
            }
        }
        return INSTANCE;
    }

    public boolean validate(int head,int tail){
        if(head>=tail){
            throw new IllegalArgumentException("Ladder head:" + head + " should be lesser than tail:" + tail);
        }
        if(head<0 || head> Constants.getBoardSize() || tail<0 || tail>Constants.getBoardSize()){
            throw new IllegalArgumentException("Ladder head:" + head + " OR  tail:" + tail + " is out of bounds");
        }
        return true;
    }
}
