package com.cache.strategy;

import com.cache.model.CacheNode;

import java.util.List;

public class ModuloDistributionStrategy implements DistributionStrategy {

    @Override
    public CacheNode getNode(String key, List<CacheNode> nodes) {
        int hash = Math.abs(key.hashCode());
        int index = hash % nodes.size();
        return nodes.get(index);
    }
}
