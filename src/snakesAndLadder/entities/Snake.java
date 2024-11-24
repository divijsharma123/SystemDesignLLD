package snakesAndLadder.entities;

import snakesAndLadder.config.Constants;

public class Snake {

    public static Snake INSTANCE;

    private Snake(){

    }

    public static Snake getInstance() {
        if(INSTANCE==null){
            synchronized (Snake.class){
                if(INSTANCE==null){
                    INSTANCE = new Snake();
                }
            }
        }
        return INSTANCE;
    }

    public boolean validate(int head,int tail){
        if(head<=tail){
            throw new IllegalArgumentException("Snake head:" + head + " should be greater than tail:" + tail);
        }
        if(head<0 || head> Constants.getBoardSize() || tail<0 || tail>Constants.getBoardSize()){
            throw new IllegalArgumentException("Snake head:" + head + " OR  tail:" + tail + " is out of bounds");
        }
        return true;

    }
}
