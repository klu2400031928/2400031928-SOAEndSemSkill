package com.example.tourism.service;

import com.example.tourism.entity.Tourist;
import com.example.tourism.repository.TouristRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class handling tourist business logic.
 * Keeps business operations separate from the controller layer.
 */
@Service
@Transactional(readOnly = true)
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    /**
     * Retrieve all registered tourists from the database.
     *
     * @return List of all tourists
     */
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    /**
     * Save a tourist record into the database.
     *
     * @param tourist the tourist to save
     * @return saved tourist entity
     */
    @Transactional
    public Tourist saveTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    /**
     * Get count of tourist records in the database.
     *
     * @return count of tourists
     */
    public long getTouristCount() {
        return touristRepository.count();
    }
}

