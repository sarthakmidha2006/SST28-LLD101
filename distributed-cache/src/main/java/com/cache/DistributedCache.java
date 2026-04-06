package com.cache;

import com.cache.db.Database;
import com.cache.eviction.EvictionPolicy;
import com.cache.eviction.EvictionPolicyFactory;
import com.cache.model.CacheNode;
import com.cache.strategy.DistributionStrategy;

import java.util.ArrayList;
import java.util.List;

public class DistributedCache {
    private final List<CacheNode> nodes;
    private final DistributionStrategy distributionStrategy;
    private final Database database;

    public DistributedCache(int numberOfNodes, int capacityPerNode,
                            DistributionStrategy distributionStrategy,
                            EvictionPolicyFactory.PolicyType evictionPolicyType,
                            Database database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            EvictionPolicy<String> evictionPolicy = EvictionPolicyFactory.create(evictionPolicyType);
            nodes.add(new CacheNode("Node-" + i, capacityPerNode, evictionPolicy));
        }
    }

    public String get(String key) {
        CacheNode node = distributionStrategy.getNode(key, nodes);
        String value = node.get(key);

        if (value == null) {
            // Cache miss — fetch from DB, store in cache
            System.out.println("[Cache MISS] Key '" + key + "' not found in " + node.getNodeId() + ". Fetching from DB...");
            value = database.get(key);
            if (value != null) {
                node.put(key, value);
            }
        }

        return value;
    }

    public void put(String key, String value) {
        CacheNode node = distributionStrategy.getNode(key, nodes);
        node.put(key, value);
        database.put(key, value); // Write-through to DB
    }
}
