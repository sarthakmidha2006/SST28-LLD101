package com.cache.strategy;

import com.cache.model.CacheNode;

import java.util.List;

public interface DistributionStrategy {
    CacheNode getNode(String key, List<CacheNode> nodes);
}
