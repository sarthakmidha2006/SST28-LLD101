package com.snakeandladder.game;

import com.snakeandladder.model.Board;
import com.snakeandladder.model.Dice;
import com.snakeandladder.model.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Dice dice;
    private final Queue<Player> players;
    private final List<Player> winners;

    public Game(Board board) {
        this.board = board;
        this.dice = new Dice();
        this.players = new LinkedList<>();
        this.winners = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        System.out.println("=== Snake and Ladder Game Started ===\n");

        while (players.size() >= 2) {
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
                winners.add(currentPlayer);
                System.out.println("=== " + currentPlayer.getName()
                        + " finishes in position #" + winners.size() + "! ===\n");
                continue;
            }

            players.add(currentPlayer);
        }

        if (players.size() == 1) {
            Player lastPlayer = players.poll();
            winners.add(lastPlayer);
        }

        System.out.println("\n=== Final Rankings ===");
        for (int i = 0; i < winners.size(); i++) {
            System.out.println((i + 1) + ". " + winners.get(i).getName());
        }
    }
}
