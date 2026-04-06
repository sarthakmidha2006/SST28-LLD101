package com.ratelimiter.algorithm;

import com.ratelimiter.model.RateLimitResult;

/**
 * Strategy interface for rate limiting algorithms.
 * Each implementation encapsulates a different rate limiting strategy.
 * All implementations must be thread-safe.
 */
public interface RateLimiter {

    /**
     * Attempts to acquire permission for a request identified by the given key.
     *
     * @param key the rate limiting key (e.g., customerId, tenantId, apiKey)
     * @return RateLimitResult indicating whether the request is allowed or denied
     */
    RateLimitResult tryAcquire(String key);
}
