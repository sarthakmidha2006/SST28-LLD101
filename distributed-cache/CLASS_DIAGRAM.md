```
+-------------------+         +------------------------+
|   <<interface>>   |         |    <<interface>>        |
|     Database      |         | DistributionStrategy   |
+-------------------+         +------------------------+
| + get(key): V     |         | + getNode(key, nodes): |
| + put(key, val)   |         |       CacheNode        |
+-------------------+         +------------------------+
        ^                              ^
        |                              |
+-------------------+         +------------------------+
| InMemoryDatabase  |         | ModuloDistribution     |
| (HashMap-backed)  |         | Strategy               |
+-------------------+         | hash(key) % numNodes   |
                              +------------------------+

+-------------------+
|   <<interface>>   |
| EvictionPolicy<K> |
+-------------------+
| + keyAccessed(K)  |
| + evict(): K      |
| + remove(K)       |
+-------------------+
        ^
        |
+---------------------+
| LRUEvictionPolicy   |
| (LinkedHashSet)     |
+---------------------+

+------------------------+           +---------------------------+
| EvictionPolicyFactory  |           |        CacheNode          |
+------------------------+           +---------------------------+
| + create(PolicyType):  |---------->| - nodeId: String          |
|   EvictionPolicy       |  creates  | - capacity: int           |
+------------------------+           | - store: Map<K,V>         |
                                     | - evictionPolicy          |
                                     +---------------------------+
                                     | + get(key): V             |
                                     | + put(key, val)           |
                                     +---------------------------+
                                              ^
                                              | has many
                                     +---------------------------+
                                     |   DistributedCache        |
                                     +---------------------------+
                                     | - nodes: List<CacheNode>  |
                                     | - distributionStrategy    |
                                     | - database: Database      |
                                     +---------------------------+
                                     | + get(key): V             |
                                     | + put(key, val)           |
                                     +---------------------------+
```
