package com.example.tourism.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Reusable service for inspecting and validating incoming authentication tokens.
 * Securely checks incoming tokens against the pre-configured valid token in application.properties.
 * Uses constant-time comparison to prevent timing attacks.
 * Does NOT expose or log the configured token.
 */
@Service
public class TokenValidationService {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidationService.class);
    private static final String BEARER_PREFIX = "Bearer ";

    private final String configuredToken;

    public TokenValidationService(
            @Value("${auth.token:${tourism.auth.token:tourism-analytics-secret-token-2026}}") String configuredToken) {
        this.configuredToken = configuredToken;
    }

    /**
     * Checks if the incoming authorization header contains a non-empty token.
     *
     * @param authHeader HTTP request header value
     * @return true if header is present and non-blank; false otherwise
     */
    public boolean isTokenPresent(String authHeader) {
        return authHeader != null && !authHeader.trim().isEmpty();
    }

    /**
     * Validates the incoming token against the configured pre-generated token.
     * Supports both direct token strings and "Bearer <token>" prefixed headers.
     * Performs a constant-time comparison to avoid timing vulnerabilities.
     *
     * @param authHeader HTTP Authorization header value
     * @return true if the token is valid; false otherwise
     */
    public boolean validateToken(String authHeader) {
        if (!isTokenPresent(authHeader)) {
            logger.warn("Authorization check failed: Token is missing or empty");
            return false;
        }

        String extractedToken = authHeader.trim();
        if (extractedToken.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            extractedToken = extractedToken.substring(BEARER_PREFIX.length()).trim();
        }

        if (extractedToken.isEmpty()) {
            logger.warn("Authorization check failed: Bearer prefix present but token body is empty");
            return false;
        }

        // Constant-time comparison between byte arrays
        byte[] configuredBytes = configuredToken.getBytes(StandardCharsets.UTF_8);
        byte[] incomingBytes = extractedToken.getBytes(StandardCharsets.UTF_8);

        boolean isValid = MessageDigest.isEqual(configuredBytes, incomingBytes);
        if (!isValid) {
            logger.warn("Authorization check failed: Provided token is invalid");
        } else {
            logger.debug("Authorization check succeeded: Token is valid");
        }

        return isValid;
    }
}

