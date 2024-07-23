package com.example.homescapebackend.security;

import java.security.SecureRandom;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 86400000; // 1 day in milliseconds
    public static final byte[] JWT_SECRET;
    public static final long JWT_REFRESH_EXPIRATION = 2592000000L; // 30 days in milliseconds


    static {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bits
        random.nextBytes(bytes);
        JWT_SECRET = bytes;
    }
}
