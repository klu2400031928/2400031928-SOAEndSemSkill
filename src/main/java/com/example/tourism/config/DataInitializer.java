package com.example.tourism.config;

import com.example.tourism.entity.Tourist;
import com.example.tourism.repository.TouristRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Seeds initial sample tourist records into the in-memory H2 database
 * upon application startup for testing and demonstration purposes.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final TouristRepository touristRepository;

    public DataInitializer(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @Override
    public void run(String... args) {
        if (touristRepository.count() == 0) {
            logger.info("Database is empty. Populating sample tourist records...");

            List<Tourist> sampleTourists = Arrays.asList(
                    new Tourist("Alice Johnson", 28, "Female", "alice.johnson@example.com", "+1-555-0101", "Bali, Indonesia"),
                    new Tourist("Bob Martinez", 35, "Male", "bob.martinez@example.com", "+1-555-0102", "Paris, France"),
                    new Tourist("Chloe Zhang", 24, "Female", "chloe.zhang@example.com", "+1-555-0103", "Tokyo, Japan"),
                    new Tourist("David Miller", 42, "Male", "david.miller@example.com", "+1-555-0104", "Rome, Italy"),
                    new Tourist("Emma Wilson", 31, "Female", "emma.wilson@example.com", "+1-555-0105", "Sydney, Australia")
            );

            touristRepository.saveAll(sampleTourists);
            logger.info("Successfully loaded {} sample tourist records.", touristRepository.count());
        } else {
            logger.info("Database already contains tourist records. Skipping initialization.");
        }
    }
}

