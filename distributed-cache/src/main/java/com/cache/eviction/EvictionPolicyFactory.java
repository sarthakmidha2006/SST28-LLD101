package com.cache.eviction;

public class EvictionPolicyFactory {

    public enum PolicyType {
        LRU
    }

    public static <K> EvictionPolicy<K> create(PolicyType type) {
        switch (type) {
            case LRU:
                return new LRUEvictionPolicy<>();
            default:
                throw new IllegalArgumentException("Unknown eviction policy: " + type);
        }
    }
}
