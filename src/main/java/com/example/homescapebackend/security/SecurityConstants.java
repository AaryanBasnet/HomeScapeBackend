package com.example.homescapebackend.security;

import java.security.SecureRandom;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 3600000; // 1 hour in milliseconds
    public static final byte[] JWT_SECRET;
    public static final long JWT_REFRESH_EXPIRATION = 86400000; // e.g., 24 hours in milliseconds


    static {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bits
        random.nextBytes(bytes);
        JWT_SECRET = bytes;
    }
}
