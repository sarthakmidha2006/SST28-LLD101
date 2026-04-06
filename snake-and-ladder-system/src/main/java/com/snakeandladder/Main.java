package com.snakeandladder;

import com.snakeandladder.game.Game;
import com.snakeandladder.model.Board;
import com.snakeandladder.model.Ladder;
import com.snakeandladder.model.Player;
import com.snakeandladder.model.Snake;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board dimension (n for n x n board): ");
        int n = scanner.nextInt();

        System.out.print("Enter number of players: ");
        int x = scanner.nextInt();

        System.out.print("Enter difficulty level (easy/hard): ");
        String difficultyLevel = scanner.next().toLowerCase();

        Board board = new Board(n);
        board.generateRandom(n, difficultyLevel);

        System.out.println("\nBoard size: " + n + " x " + n + " (" + (n * n) + " cells)");
        System.out.println("Difficulty: " + difficultyLevel);

        System.out.println("\nSnakes:");
        for (Snake snake : board.getSnakes()) {
            System.out.println("  " + snake.getHead() + " -> " + snake.getTail());
        }

        System.out.println("Ladders:");
        for (Ladder ladder : board.getLadders()) {
            System.out.println("  " + ladder.getStart() + " -> " + ladder.getEnd());
        }
        System.out.println();

        Game game = new Game(board);
        scanner.nextLine();
        for (int i = 1; i <= x; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            game.addPlayer(new Player(i, name));
        }
        System.out.println();

        game.start();
        scanner.close();
    }
}
