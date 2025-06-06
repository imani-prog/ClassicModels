package com.classicmodels.classicmodels.Service;

import com.classicmodels.classicmodels.Entities.Office;
import com.classicmodels.classicmodels.Repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface OfficeService {
    Office saveOffice(Office office);
    String generateOfficeId();
}
