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

//    @DeleteMapping
//    public ResponseEntity<Office> deleteOffice(@RequestBody Office office) {
//        officeService.deleteOffice(office);
//        return ResponseEntity.ok(office);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable String id) {
        officeService.deleteOfficeById(id);
        return ResponseEntity.noContent().build();
    }

}
