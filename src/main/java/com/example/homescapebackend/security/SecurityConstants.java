package com.example.homescapebackend.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 86400000; // 1 day in milliseconds
    /**
     * The key that signs the tokens. Set JWT_SECRET (at least 32 characters) so that logins survive a restart.
     * If it is not set, a random key is made on every start and everyone is logged out when the server restarts.
     */
    public static final byte[] JWT_SECRET;
    public static final long JWT_REFRESH_EXPIRATION = 2592000000L; // 30 days in milliseconds

    static {
        String configured = System.getenv("JWT_SECRET");
        if (configured != null && configured.length() >= 32) {
            JWT_SECRET = configured.getBytes(StandardCharsets.UTF_8);
        } else {
            System.err.println("JWT_SECRET is not set (or shorter than 32 characters): using a random key, so logins end when the server restarts.");
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[32]; // 256 bits
            random.nextBytes(bytes);
            JWT_SECRET = bytes;
        }
    }
}
