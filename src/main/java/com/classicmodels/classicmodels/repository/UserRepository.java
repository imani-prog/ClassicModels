package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndEnabled(String email, boolean enabled);

    @Query("SELECT u.id FROM User u WHERE u.role = :role ORDER BY u.id DESC LIMIT 1")
    String findLastIdByRole(@Param("role") User.Role role);
}
