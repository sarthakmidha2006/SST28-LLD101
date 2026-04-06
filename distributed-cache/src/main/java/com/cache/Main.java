package com.cache;

import com.cache.db.Database;
import com.cache.db.InMemoryDatabase;
import com.cache.eviction.EvictionPolicyFactory;
import com.cache.strategy.DistributionStrategy;
import com.cache.strategy.ModuloDistributionStrategy;

public class Main {
    public static void main(String[] args) {
        Database database = new InMemoryDatabase();
        DistributionStrategy strategy = new ModuloDistributionStrategy();

        // 3 nodes, capacity 2 each, LRU eviction
        DistributedCache cache = new DistributedCache(
                3, 2, strategy, EvictionPolicyFactory.PolicyType.LRU, database
        );

        System.out.println("=== Cache Miss -> Fetches from DB ===");
        System.out.println("user:1 = " + cache.get("user:1"));
        System.out.println();

        System.out.println("=== Cache Hit -> Returns from Cache ===");
        System.out.println("user:1 = " + cache.get("user:1"));
        System.out.println();

        System.out.println("=== Put directly into cache + DB ===");
        cache.put("user:6", "Frank");
        System.out.println();

        System.out.println("=== Fill up a node to trigger eviction ===");
        cache.get("user:2");
        cache.get("user:3");
        cache.get("user:4");
        cache.get("user:5");
        System.out.println();

        // These puts will cause eviction on whichever node they map to
        System.out.println("=== Trigger eviction by adding more keys ===");
        cache.put("user:7", "Grace");
        cache.put("user:8", "Heidi");
        cache.put("user:9", "Ivan");
        System.out.println();

        System.out.println("=== Verify evicted key causes cache miss ===");
        System.out.println("user:1 = " + cache.get("user:1"));
    }
}
