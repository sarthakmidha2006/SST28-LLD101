package com.snakeandladder;

import com.snakeandladder.game.Game;
import com.snakeandladder.model.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(10);

        board.addSnake(new Snake(99, 10));
        board.addSnake(new Snake(70, 30));
        board.addSnake(new Snake(52, 29));
        board.addSnake(new Snake(45, 7));

        board.addLadder(new Ladder(3, 22));
        board.addLadder(new Ladder(8, 34));
        board.addLadder(new Ladder(28, 76));
        board.addLadder(new Ladder(58, 92));

        Player p1 = new Player(1, "Alice");
        Player p2 = new Player(2, "Bob");
        Player p3 = new Player(3, "Charlie");

        Game game = new Game(board);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.start();
    }
}
