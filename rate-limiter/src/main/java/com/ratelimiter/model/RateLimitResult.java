package com.ratelimiter.model;

public class RateLimitResult {
    private final boolean allowed;
    private final int remainingRequests;

    private RateLimitResult(boolean allowed, int remainingRequests) {
        this.allowed = allowed;
        this.remainingRequests = remainingRequests;
    }

    public static RateLimitResult allowed(int remaining) {
        return new RateLimitResult(true, remaining);
    }

    public static RateLimitResult denied(int remaining) {
        return new RateLimitResult(false, remaining);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemainingRequests() {
        return remainingRequests;
    }

    @Override
    public String toString() {
        return (allowed ? "ALLOWED" : "DENIED") + " (remaining: " + remainingRequests + ")";
    }
}
