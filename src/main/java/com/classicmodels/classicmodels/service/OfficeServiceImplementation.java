package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Office;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        try {
            office.setOfficeCode(generateOfficeId());
            log.info("\nSaving office with code: {}", office.getOfficeCode());
            return officeRepository.save(office);
        } catch (Exception e) {
            log.error("Failed to save office: {}", e.getMessage(), e);
            return null;
        }
    }




//    @Override
//    public Office saveOffice(Office office) {
//        office.setOfficeCode(generateOfficeId());
//        log.info("\nSaving office with code: {}", office.getOfficeCode());
//        return officeRepository.save(office);
//    }


    @Override
    public String generateOfficeId() {
        String officeCode =  "OFF"+ String.valueOf(System.currentTimeMillis());
        if (officeCode.length() > 10) {
            return officeCode.substring(0, 10);
        } else {
            return officeCode;
        }
    }

    @Override
    public List<Office> getAllOffices() {
        log.info("Fetching all offices from the database");
        return officeRepository.findAll();
    }


    @Override
    public Office getOfficeById(String id) {
        return officeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOffice(Office office) {
        log.info("Deleting office with code: {}", office.getOfficeCode());
        officeRepository.delete(office);
    }

    @Override
    public void deleteOfficeById(String officeCode) {
        officeRepository.deleteById(officeCode);
    }


//    @Override
//    public String generateOfficeId() {
//        return "OFF-"+ String.valueOf(System.currentTimeMillis());
//    }

    @Override
    public Office updateOffice(String id, Office officeDetails) {
        Office existingOffice = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));
        existingOffice.setCity(officeDetails.getCity());
        existingOffice.setPhone(officeDetails.getPhone());
        existingOffice.setAddressLine1(officeDetails.getAddressLine1());
        existingOffice.setAddressLine2(officeDetails.getAddressLine2());
        existingOffice.setState(officeDetails.getState());
        existingOffice.setCountry(officeDetails.getCountry());
        existingOffice.setPostalCode(officeDetails.getPostalCode());
        existingOffice.setTerritory(officeDetails.getTerritory());
        return officeRepository.save(existingOffice);
    }

    @Override
    public List<Office> searchOffices(String city, String country) {
        if (city != null && !city.isEmpty() && country != null && !country.isEmpty()) {
            return officeRepository.findByCityIgnoreCaseAndCountryIgnoreCase(city, country);
        } else if (city != null && !city.isEmpty()) {
            return officeRepository.findByCityIgnoreCase(city);
        } else if (country != null && !country.isEmpty()) {
            return officeRepository.findByCountryIgnoreCase(country);
        } else {
            return officeRepository.findAll();
        }
    }

}
