package com.demo.clinic.controller;

import com.demo.clinic.beans.Physician;
import com.demo.clinic.db.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PhysicianController {

    @Autowired
    PhysicianRepository physicianRepository;

    @PostMapping("/physicians")
    public ResponseEntity<Physician> createPhysician(@RequestBody Physician physician) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Date createdTime = new Date(currentTimeMillis);

            Physician _physician = physicianRepository
                    .save(new Physician(physician.getName(), createdTime, physician.getCreatedBy()));
            return new ResponseEntity<>(_physician, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/physicians/{id}")
    public ResponseEntity<Physician> updatePhysician(@PathVariable("id") int id, @RequestBody Physician physician) {
        Optional<Physician> physicianData = physicianRepository.findById(id);

        if (physicianData.isPresent()) {
            long currentTimeMillis = System.currentTimeMillis();
            Date modifiedTime = new Date(currentTimeMillis);

            Physician _physician = physicianData.get();
            _physician.setName(physician.getName());
            _physician.setModifiedBy(physician.getModifiedBy());
            _physician.setModifiedTime(modifiedTime);
            return new ResponseEntity<>(physicianRepository.save(_physician), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
