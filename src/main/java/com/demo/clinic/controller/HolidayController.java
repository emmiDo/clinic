package com.demo.clinic.controller;

import com.demo.clinic.beans.Holiday;
import com.demo.clinic.db.HolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HolidayController {

    static final Logger log = LoggerFactory.getLogger(HolidayController.class);

    @Autowired
    HolidayRepository holidayRepository;

    @PostMapping("/holidays")
    public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Date createdTime = new Date(currentTimeMillis);

            Holiday _holiday = holidayRepository
                    .save(new Holiday(holiday.getHoliday(), holiday.getName(), createdTime, holiday.getCreatedBy()));
            return new ResponseEntity<>(_holiday, HttpStatus.CREATED);
        } catch (Exception e) {
            log.debug("Error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/holidays/{id}")
    public ResponseEntity<Holiday> updateHoliday(@PathVariable("id") long id, @RequestBody Holiday holiday) {
        Optional<Holiday> holidayData = holidayRepository.findById(id);

        if (holidayData.isPresent()) {
            long currentTimeMillis = System.currentTimeMillis();
            Date modifiedTime = new Date(currentTimeMillis);

            Holiday _holiday = holidayData.get();
            _holiday.setHoliday(holiday.getHoliday());
            _holiday.setName(holiday.getName());
            _holiday.setModifiedBy(holiday.getModifiedBy());
            _holiday.setModifiedTime(modifiedTime);
            return new ResponseEntity<>(holidayRepository.save(_holiday), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
