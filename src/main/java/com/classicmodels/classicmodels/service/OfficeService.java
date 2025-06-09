package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Office;
import org.springframework.stereotype.Service;

@Service
public interface OfficeService {
    Office saveOffice(Office office);
    String generateOfficeId();
}
