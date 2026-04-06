package com.ratelimiter.factory;

import com.ratelimiter.algorithm.FixedWindowCounter;
import com.ratelimiter.algorithm.RateLimiter;
import com.ratelimiter.algorithm.SlidingWindowCounter;
import com.ratelimiter.model.RateLimitConfig;

/**
 * Factory for creating RateLimiter instances.
 * Callers choose an algorithm type without knowing implementation details.
 * Adding a new algorithm requires only a new enum value and one case in the switch.
 */
public class RateLimiterFactory {

    public enum Algorithm {
        FIXED_WINDOW,
        SLIDING_WINDOW
        // Future: TOKEN_BUCKET, LEAKY_BUCKET, SLIDING_LOG
    }

    public static RateLimiter create(Algorithm algorithm, RateLimitConfig config) {
        switch (algorithm) {
            case FIXED_WINDOW:
                return new FixedWindowCounter(config);
            case SLIDING_WINDOW:
                return new SlidingWindowCounter(config);
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }
}
