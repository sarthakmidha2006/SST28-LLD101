package com.snakeandladder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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

    public void generateRandom(int count, String difficultyLevel) {
        Random random = new Random();
        Set<Integer> occupied = new HashSet<>();
        occupied.add(1);
        occupied.add(size);

        for (int i = 0; i < count; i++) {
            int head, tail;
            do {
                head = random.nextInt(size - 2) + 2;
                tail = random.nextInt(head - 1) + 1;
            } while (occupied.contains(head) || occupied.contains(tail) || wouldCreateCycle(head, tail));
            occupied.add(head);
            occupied.add(tail);
            addSnake(new Snake(head, tail));
        }

        int maxJump = difficultyLevel.equals("hard") ? size / 4 : size / 2;

        for (int i = 0; i < count; i++) {
            int start, end;
            do {
                start = random.nextInt(size - 2) + 2;
                end = start + random.nextInt(Math.min(maxJump, size - start)) + 1;
                if (end >= size) end = size - 1;
            } while (occupied.contains(start) || occupied.contains(end) || wouldCreateCycle(start, end));
            occupied.add(start);
            occupied.add(end);
            addLadder(new Ladder(start, end));
        }
    }

    private boolean wouldCreateCycle(int from, int to) {
        Set<Integer> visited = new HashSet<>();
        int current = to;
        while (true) {
            if (!visited.add(current)) return true;
            if (snakeMap.containsKey(current)) {
                current = snakeMap.get(current);
            } else if (ladderMap.containsKey(current)) {
                current = ladderMap.get(current);
            } else {
                break;
            }
        }
        return false;
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

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
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
