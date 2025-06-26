package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

