package com.example.tourism.controller;

import com.example.tourism.entity.Tourist;
import com.example.tourism.exception.ErrorResponse;
import com.example.tourism.service.TokenValidationService;
import com.example.tourism.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Tourism Analytics Dashboard.
 * Exposes endpoints to retrieve tourist records with token validation.
 */
@RestController
@RequestMapping("/api")
public class TouristController {

    private final TouristService touristService;
    private final TokenValidationService tokenValidationService;

    public TouristController(TouristService touristService, TokenValidationService tokenValidationService) {
        this.touristService = touristService;
        this.tokenValidationService = tokenValidationService;
    }

    /**
     * Retrieve all registered tourists from the database.
     * Requires a valid token in the HTTP Authorization header.
     *
     * @param authHeader incoming HTTP Authorization request header
     * @return HTTP 200 with list of tourists if token is valid,
     *         or HTTP 401 Unauthorized if token is missing or invalid.
     */
    @GetMapping(value = "/tourists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTourists(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 1. Inspect and validate token before accessing tourist records
        if (!tokenValidationService.validateToken(authHeader)) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized",
                    "Missing or invalid Authorization token. Access denied.",
                    "/api/tourists"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // 2. Token is valid -> retrieve and return all tourists from service layer
        List<Tourist> tourists = touristService.getAllTourists();
        return ResponseEntity.ok(tourists);
    }
}

