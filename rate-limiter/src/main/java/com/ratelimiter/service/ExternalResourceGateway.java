package com.ratelimiter.service;

import com.ratelimiter.algorithm.RateLimiter;
import com.ratelimiter.model.RateLimitResult;

/**
 * Gateway that guards external (paid) resource calls with rate limiting.
 *
 * Internal services use this gateway instead of calling the external resource directly.
 * The rate limiter is injected via constructor (Dependency Inversion),
 * so the algorithm can be swapped without changing any business logic.
 */
public class ExternalResourceGateway {

    private final RateLimiter rateLimiter;

    public ExternalResourceGateway(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    /**
     * Attempts to call the external resource on behalf of the given key.
     *
     * @param key identifies who is making the call (customerId, tenantId, etc.)
     * @return true if the external call was made, false if rate-limited
     */
    public boolean callExternalResource(String key) {
        RateLimitResult result = rateLimiter.tryAcquire(key);

        if (result.isAllowed()) {
            // Simulate the actual external API call
            System.out.println("  [Gateway] External call ALLOWED for key=" + key
                    + " | " + result);
            performExternalCall(key);
            return true;
        } else {
            System.out.println("  [Gateway] External call DENIED for key=" + key
                    + " | " + result);
            return false;
        }
    }

    private void performExternalCall(String key) {
        // Simulate external paid API call (e.g., SMS, payment, AI inference)
        System.out.println("  [External API] Processing request for key=" + key);
    }
}
