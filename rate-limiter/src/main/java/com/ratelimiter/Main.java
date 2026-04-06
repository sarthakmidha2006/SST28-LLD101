package com.ratelimiter;

import com.ratelimiter.algorithm.RateLimiter;
import com.ratelimiter.factory.RateLimiterFactory;
import com.ratelimiter.model.RateLimitConfig;
import com.ratelimiter.service.ExternalResourceGateway;
import com.ratelimiter.service.InternalService;

public class Main {
    public static void main(String[] args) {
        // Config: tenant T1 is allowed 5 external calls per minute
        RateLimitConfig config = RateLimitConfig.perMinute(5);

        System.out.println("============================================");
        System.out.println("  DEMO 1: Fixed Window Counter");
        System.out.println("  Config: " + config);
        System.out.println("============================================\n");

        runDemo(RateLimiterFactory.Algorithm.FIXED_WINDOW, config);

        System.out.println("\n============================================");
        System.out.println("  DEMO 2: Sliding Window Counter");
        System.out.println("  Config: " + config);
        System.out.println("============================================\n");

        runDemo(RateLimiterFactory.Algorithm.SLIDING_WINDOW, config);
    }

    /**
     * Demonstrates the rate limiter with a given algorithm.
     * Business logic does NOT change — only the algorithm is swapped via the factory.
     */
    private static void runDemo(RateLimiterFactory.Algorithm algorithm, RateLimitConfig config) {
        // 1. Create rate limiter using factory (Strategy Pattern)
        RateLimiter rateLimiter = RateLimiterFactory.create(algorithm, config);

        // 2. Inject into gateway (Dependency Inversion)
        ExternalResourceGateway gateway = new ExternalResourceGateway(rateLimiter);

        // 3. Internal service uses gateway — unaware of rate limiting details
        InternalService service = new InternalService(gateway);

        // Simulate 12 requests from tenant T1
        // Only even-numbered requests (2,4,6,8,10,12) need external calls = 6 calls
        // Limit is 5, so the 6th external call (request #12) should be denied
        for (int i = 1; i <= 12; i++) {
            service.handleRequest("T1", i);
            System.out.println();
        }

        // Simulate requests from a different tenant — has its own quota
        System.out.println("--- Tenant T2 (separate quota) ---\n");
        for (int i = 2; i <= 4; i += 2) {
            service.handleRequest("T2", i);
            System.out.println();
        }
    }
}
