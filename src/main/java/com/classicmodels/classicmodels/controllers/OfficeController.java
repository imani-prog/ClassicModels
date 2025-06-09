package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.entities.Office;
import com.classicmodels.classicmodels.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
