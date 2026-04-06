package com.ratelimiter.service;

/**
 * Simulates an internal service that processes client API requests.
 * Only some requests require an external resource call.
 */
public class InternalService {

    private final ExternalResourceGateway gateway;

    public InternalService(ExternalResourceGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Handles a client request. Business logic decides whether an external call is needed.
     *
     * @param customerId the customer making the request
     * @param requestId  an identifier for the request
     */
    public void handleRequest(String customerId, int requestId) {
        System.out.println("Request #" + requestId + " from " + customerId);

        // Business logic: only even-numbered requests need external resource
        boolean needsExternalCall = (requestId % 2 == 0);

        if (!needsExternalCall) {
            System.out.println("  [Service] No external call needed. Handled internally.");
            return;
        }

        // Rate limiter is consulted only when external call is actually needed
        boolean success = gateway.callExternalResource(customerId);
        if (!success) {
            System.out.println("  [Service] Fallback: returning cached/default response.");
        }
    }
}
