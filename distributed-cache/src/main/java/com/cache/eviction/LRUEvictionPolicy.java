package com.cache.eviction;

import java.util.LinkedHashSet;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashSet<K> accessOrder = new LinkedHashSet<>();

    @Override
    public void keyAccessed(K key) {
        accessOrder.remove(key);
        accessOrder.add(key); // Most recently used goes to the end
    }

    @Override
    public K evict() {
        if (accessOrder.isEmpty()) {
            return null;
        }
        K lruKey = accessOrder.iterator().next(); // First element = least recently used
        accessOrder.remove(lruKey);
        return lruKey;
    }

    @Override
    public void remove(K key) {
        accessOrder.remove(key);
    }
}
