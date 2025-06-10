package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Office;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfficeServiceImplementation implements  OfficeService {
    private final OfficeRepository officeRepository;
    //private Logger log;


//    @Override
//    public Office saveOffice(Office office) {
//        office.setOfficeCode(generateOfficeId());
//        return officeRepository.save(office);
//    }

    @Override
    public Office saveOffice(Office office) {
        office.setOfficeCode(generateOfficeId());
        log.info("\nSaving office with code: {}", office.getOfficeCode());
        return officeRepository.save(office);
    }


    @Override
    public String generateOfficeId() {
        String officeCode =  "OFF"+ String.valueOf(System.currentTimeMillis());
        if (officeCode.length() > 10) {
            return officeCode.substring(0, 10);
        } else {
            return officeCode;
        }
    }

//    @Override
//    public String generateOfficeId() {
//        return "OFF-"+ String.valueOf(System.currentTimeMillis());
//    }

}
