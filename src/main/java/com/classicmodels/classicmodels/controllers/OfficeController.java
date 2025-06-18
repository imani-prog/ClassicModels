package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.entities.Office;
import com.classicmodels.classicmodels.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {


    private final OfficeService officeService;

    @PostMapping("/save")
    public ResponseEntity<Office> saveOffice(@RequestBody Office office) {
        Office savedOffice = officeService.saveOffice(office);
        return ResponseEntity.ok(new Office());
    }

    @GetMapping
    public ResponseEntity<List<Office>> getAllOffices() {
        List<Office> offices = officeService.getAllOffices();
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Office> getOfficeById(@PathVariable String id) {
        Office office = officeService.getOfficeById(id);
        if (office != null) {
            return ResponseEntity.ok(office);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable String id) {
        officeService.deleteOfficeById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@PathVariable String id, @RequestBody Office officeDetails) {
        try {
            Office updated = officeService.updateOffice(id, officeDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Office>> searchOffices(@RequestParam(required = false) String city,
                                                     @RequestParam(required = false) String country) {
        List<Office> offices = officeService.searchOffices(city, country);
        return ResponseEntity.ok(offices);
    }

}
