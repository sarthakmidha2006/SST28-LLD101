package com.ratelimiter.algorithm;

import com.ratelimiter.model.RateLimitConfig;
import com.ratelimiter.model.RateLimitResult;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fixed Window Counter rate limiter.
 *
 * Divides time into fixed windows (e.g., each minute) and counts requests per window.
 * When a new window starts, the counter resets.
 *
 * Trade-offs:
 * - Pros: Simple, low memory (one counter per key), O(1) per request
 * - Cons: Burst problem at window boundaries — a client can make 2x the limit
 *         by sending requests at the end of one window and start of the next
 */
public class FixedWindowCounter implements RateLimiter {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, WindowState> windowMap = new ConcurrentHashMap<>();

    public FixedWindowCounter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public RateLimitResult tryAcquire(String key) {
        long now = System.currentTimeMillis();
        long currentWindow = now / config.getWindowSizeMillis();

        WindowState state = windowMap.compute(key, (k, existing) -> {
            if (existing == null || existing.windowId != currentWindow) {
                return new WindowState(currentWindow, new AtomicInteger(0));
            }
            return existing;
        });

        int currentCount = state.counter.incrementAndGet();

        if (currentCount <= config.getMaxRequests()) {
            return RateLimitResult.allowed(config.getMaxRequests() - currentCount);
        } else {
            state.counter.decrementAndGet(); // rollback
            return RateLimitResult.denied(0);
        }
    }

    private static class WindowState {
        final long windowId;
        final AtomicInteger counter;

        WindowState(long windowId, AtomicInteger counter) {
            this.windowId = windowId;
            this.counter = counter;
        }
    }
}
