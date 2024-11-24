package snakesAndLadder.service;

import snakesAndLadder.config.Constants;
import snakesAndLadder.entities.Board;
import snakesAndLadder.entities.Player;

import java.util.Deque;

public class GameService {
    private static GameService INSTANCE;
    private Board board;
    private Deque<Player> players;


    private GameService(){
    }
    public static GameService getInstance(){
        if(INSTANCE==null){
            synchronized (GameService.class){
                if(INSTANCE==null){
                    INSTANCE = new GameService();
                }
            }
        }
        return INSTANCE;
    }

    public void initialize(Board board, Deque<Player> players){
        this.board = board;
        this.players = players;
    }
    public void play(){
        boolean isGameOn = true;
        while(isGameOn){
            Player player = players.removeFirst();
            int dicePos = DiceService.getInstance().rollDice();
            int playerPos = player.getPosition();
            int newPlayerPos = playerPos + dicePos;
            if(newPlayerPos > Constants.getBoardSize()){
                System.out.println("Player: " + player.getName() + " got out of bounds: " + newPlayerPos);
            }else if(newPlayerPos == Constants.getBoardSize()){
                isGameOn = false;
                System.out.println("Player: " + player.getName() + " wins");
            }else{
                while(board.getSnakesAndLadders().containsKey(newPlayerPos)){
                    newPlayerPos = board.getSnakesAndLadders().get(newPlayerPos);
                }
                if(newPlayerPos == Constants.getBoardSize()){
                    isGameOn = false;
                    System.out.println("Player: " + player.getName() + " wins");
                }

                player.setPosition(newPlayerPos);
                System.out.println("Player: " + player.getName() + " is at Position:" + player.getPosition());
            }
            players.addLast(player);
        }
    }
}
