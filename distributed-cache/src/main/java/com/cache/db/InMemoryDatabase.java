package com.cache.db;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {
    private final Map<String, String> store = new HashMap<>();

    public InMemoryDatabase() {
        // Pre-populate with some data to simulate a real DB
        store.put("user:1", "Alice");
        store.put("user:2", "Bob");
        store.put("user:3", "Charlie");
        store.put("user:4", "Diana");
        store.put("user:5", "Eve");
    }

    @Override
    public String get(String key) {
        System.out.println("[DB] Fetching key: " + key);
        return store.get(key);
    }

    @Override
    public void put(String key, String value) {
        System.out.println("[DB] Storing key: " + key);
        store.put(key, value);
    }
}
