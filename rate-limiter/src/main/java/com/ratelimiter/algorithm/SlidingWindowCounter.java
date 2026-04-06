package com.ratelimiter.algorithm;

import com.ratelimiter.model.RateLimitConfig;
import com.ratelimiter.model.RateLimitResult;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Sliding Window Counter rate limiter.
 *
 * Combines the simplicity of Fixed Window with a weighted overlap from the previous window
 * to smooth out the boundary burst problem.
 *
 * Formula: effectiveCount = previousWindowCount * overlapRatio + currentWindowCount
 * where overlapRatio = (windowSize - elapsed) / windowSize
 *
 * Trade-offs:
 * - Pros: Smooths out burst spikes at window boundaries, still O(1) and low memory
 * - Cons: Only an approximation — not exact like Sliding Log. Can still allow
 *         slightly more than the limit in edge cases, but much better than Fixed Window
 */
public class SlidingWindowCounter implements RateLimiter {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, SlidingWindowState> stateMap = new ConcurrentHashMap<>();

    public SlidingWindowCounter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public RateLimitResult tryAcquire(String key) {
        long now = System.currentTimeMillis();
        long windowSize = config.getWindowSizeMillis();
        long currentWindowStart = (now / windowSize) * windowSize;

        SlidingWindowState state = stateMap.compute(key, (k, existing) -> {
            if (existing == null) {
                return new SlidingWindowState(currentWindowStart, 0, 0);
            }
            if (existing.currentWindowStart != currentWindowStart) {
                // Slide: current becomes previous, start a fresh current
                if (currentWindowStart - existing.currentWindowStart == windowSize) {
                    return new SlidingWindowState(currentWindowStart, 0, existing.currentCount);
                } else {
                    // More than one window has passed — previous window data is stale
                    return new SlidingWindowState(currentWindowStart, 0, 0);
                }
            }
            return existing;
        });

        synchronized (state) {
            long elapsedInCurrentWindow = now - currentWindowStart;
            double overlapRatio = (double) (windowSize - elapsedInCurrentWindow) / windowSize;
            double effectiveCount = state.previousCount * overlapRatio + state.currentCount;

            if (effectiveCount + 1 <= config.getMaxRequests()) {
                state.currentCount++;
                int remaining = (int) (config.getMaxRequests() - (effectiveCount + 1));
                return RateLimitResult.allowed(Math.max(remaining, 0));
            } else {
                return RateLimitResult.denied(0);
            }
        }
    }

    private static class SlidingWindowState {
        long currentWindowStart;
        int currentCount;
        int previousCount;

        SlidingWindowState(long currentWindowStart, int currentCount, int previousCount) {
            this.currentWindowStart = currentWindowStart;
            this.currentCount = currentCount;
            this.previousCount = previousCount;
        }
    }
}
