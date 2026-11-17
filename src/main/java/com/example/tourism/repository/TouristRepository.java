package com.example.tourism.repository;

import com.example.tourism.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for Tourist entity.
 * Provides standard CRUD and bulk retrieval capabilities.
 */
@Repository
public interface TouristRepository extends JpaRepository<Tourist, Long> {
}

