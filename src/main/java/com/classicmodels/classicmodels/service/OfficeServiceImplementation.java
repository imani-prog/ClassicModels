package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Office;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficeServiceImplementation implements  OfficeService {
    private final OfficeRepository officeRepository;


    @Override
    public Office saveOffice(Office office) {
        office.setOfficeCode(generateOfficeId());
        return officeRepository.save(office);
    }

    @Override
    public String generateOfficeId() {
        return "OFF-"+ String.valueOf(System.currentTimeMillis());
    }
}
