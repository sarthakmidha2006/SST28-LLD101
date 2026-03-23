package com.snakeandladder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int size;
    private final List<Snake> snakes;
    private final List<Ladder> ladders;
    private final Map<Integer, Integer> snakeMap;
    private final Map<Integer, Integer> ladderMap;

    public Board(int n) {
        this.size = n * n;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.snakeMap = new HashMap<>();
        this.ladderMap = new HashMap<>();
    }

    public void addSnake(Snake snake) {
        snakes.add(snake);
        snakeMap.put(snake.getHead(), snake.getTail());
    }

    public void addLadder(Ladder ladder) {
        ladders.add(ladder);
        ladderMap.put(ladder.getStart(), ladder.getEnd());
    }

    public int getSize() {
        return size;
    }

    public int getNewPosition(int position) {
        if (snakeMap.containsKey(position)) {
            int newPos = snakeMap.get(position);
            System.out.println("  Bitten by snake at " + position + "! Goes down to " + newPos);
            return newPos;
        }
        if (ladderMap.containsKey(position)) {
            int newPos = ladderMap.get(position);
            System.out.println("  Climbed ladder at " + position + "! Goes up to " + newPos);
            return newPos;
        }
        return position;
    }
}
