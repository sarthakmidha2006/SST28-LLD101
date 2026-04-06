package com.cache.db;

public interface Database {
    String get(String key);
    void put(String key, String value);
}
