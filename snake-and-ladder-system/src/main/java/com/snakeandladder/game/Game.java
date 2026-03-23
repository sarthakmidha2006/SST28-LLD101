package com.snakeandladder.game;

import com.snakeandladder.model.Board;
import com.snakeandladder.model.Dice;
import com.snakeandladder.model.Player;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Dice dice;
    private final Queue<Player> players;

    public Game(Board board) {
        this.board = board;
        this.dice = new Dice();
        this.players = new LinkedList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        System.out.println("=== Snake and Ladder Game Started ===\n");

        while (true) {
            Player currentPlayer = players.poll();
            int diceValue = dice.roll();
            int oldPosition = currentPlayer.getCurrentPosition();
            int newPosition = oldPosition + diceValue;

            System.out.println(currentPlayer.getName() + " rolled a " + diceValue
                    + " (position: " + oldPosition + ")");

            if (newPosition > board.getSize()) {
                System.out.println("  Cannot move beyond " + board.getSize() + ". Stays at " + oldPosition + "\n");
                players.add(currentPlayer);
                continue;
            }

            newPosition = board.getNewPosition(newPosition);
            currentPlayer.setCurrentPosition(newPosition);
            System.out.println("  " + currentPlayer.getName() + " moves to " + newPosition + "\n");

            if (newPosition == board.getSize()) {
                System.out.println("=== " + currentPlayer.getName() + " WINS the game! ===");
                break;
            }

            players.add(currentPlayer);
        }
    }
}
