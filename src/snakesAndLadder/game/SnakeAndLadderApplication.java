package snakesAndLadder.game;

import snakesAndLadder.entities.*;
import snakesAndLadder.service.DiceService;
import snakesAndLadder.service.GameService;

import java.util.*;

public class SnakeAndLadderApplication {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // Input snakes
        Snake s = Snake.getInstance();
        int numSnakes = scanner.nextInt();
        HashMap<Integer, Integer> snakesAndLadders = new HashMap<>();
        for (int i = 0; i < numSnakes; i++) {
            int head = scanner.nextInt();
            int tail = scanner.nextInt();
            if(s.validate(head,tail)){
                snakesAndLadders.put(head, tail);
            }
        }

        // Input ladders
        Ladder l = Ladder.getInstance();
        int numLadders = scanner.nextInt();
        for (int i = 0; i < numLadders; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            if(l.validate(start,end)){
                snakesAndLadders.put(start, end);
            }
        }

        // Input players
        int numPlayers = scanner.nextInt();
        Deque<Player> players = new ArrayDeque<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(scanner.next()));
        }

        // Initialize board and services
        Board board = new Board(snakesAndLadders);
        GameService gameService = GameService.getInstance();

        gameService.initialize(board, players);

        // Start the game
        gameService.play();



    }

}
