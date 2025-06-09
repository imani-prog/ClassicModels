package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {
    // This interface will automatically provide CRUD operations for the Office entity.
    // Additional custom query methods can be defined here if needed.
}