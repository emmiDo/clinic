package com.demo.clinic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.demo.clinic.beans.Message;
import com.demo.clinic.beans.Visit;
import com.demo.clinic.db.HolidayRepository;
import com.demo.clinic.db.PatientRepository;
import com.demo.clinic.db.PhysicianRepository;
import com.demo.clinic.db.VisitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class VisitController {

    static final Logger log = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PhysicianRepository physicianRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/visits")
    public ResponseEntity<Object> createVisit(@RequestBody Visit visit) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Date createdTime = new Date(currentTimeMillis);

            Date visitTime = sdf.parse(sdf.format(visit.getVisitTime()));
            log.info("Visit: " + visitTime);
            log.info(holidayRepository.findByHoliday(visitTime)+"");

            if (holidayRepository.findByHoliday(visitTime).size() > 0) {
                log.error("Visit day is a holiday");
                Message message = new Message("Visit day is a holiday");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            Date createdDate = sdf.parse(sdf.format(createdTime));
            if (holidayRepository.findByHoliday(createdDate).size() > 0) {
                log.error("Today is a holiday.");
                Message message = new Message("Today is a holiday.");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            if (!physicianRepository.findById(visit.getPhysicianId()).isPresent()) {
                log.error("Wrong physician ID");
                Message message = new Message("Wrong physician ID");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            if (!patientRepository.findById(visit.getPatientId()).isPresent()) {
                log.error("Wrong patient ID");
                Message message = new Message("Wrong patient ID");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            Visit _visit = visitRepository.save(new Visit(
                    visit.getVisitTime(), visit.getPhysicianId(), visit.getPatientId(),
                    visit.getReason(), createdTime, visit.getCreatedBy()));
            log.info("created");
            return new ResponseEntity<>(_visit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable("id") long id) {
        Optional<Visit> visit = visitRepository.findById(id);

        if (visit.isPresent()) {
            return new ResponseEntity<>(visit.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<Object> updateVisit(@PathVariable("id") long id, @RequestBody Visit visit) {
        Optional<Visit> visitToUpdate = visitRepository.findById(id);

        if (visitToUpdate.isPresent()) {
            long currentTimeMillis = System.currentTimeMillis();
            Date modifiedTime = new Date(currentTimeMillis);

            Date visitTime = null;
            try {
                visitTime = sdf.parse(sdf.format(visit.getVisitTime()));
            } catch (ParseException e) {
                log.error("Parsing error", e);
            }
            log.info("Visit: " + visitTime);
            log.info(holidayRepository.findByHoliday(visitTime)+"");

            if (holidayRepository.findByHoliday(visitTime).size() > 0) {
                log.error("Visit day is a holiday");
                Message message = new Message("Visit day is a holiday");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            Date modifiedDate = null;
            try {
                modifiedDate = sdf.parse(sdf.format(modifiedTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (holidayRepository.findByHoliday(modifiedDate).size() > 0) {
                log.error("Today is a holiday, you can't modify.");
                Message message = new Message("Today is a holiday, you can't modify.");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            Visit _visit = visitToUpdate.get();
            _visit.setVisitTime(visit.getVisitTime());
            _visit.setPhysicianId(visit.getPhysicianId());
            _visit.setReason(visit.getReason());
            _visit.setModifiedBy(visit.getModifiedBy());
            _visit.setModifiedTime(modifiedTime);
            return new ResponseEntity<>(visitRepository.save(_visit), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<Object> deleteVisit(@PathVariable("id") long id) {
        try {
            long currentTimeMillis = System.currentTimeMillis();

            Date currentDate = null;
            try {
                currentDate = sdf.parse(sdf.format(new Date(currentTimeMillis)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (holidayRepository.findByHoliday(currentDate).size() > 0) {
                log.error("Today is a holiday, you can't delete.");
                Message message = new Message("Today is a holiday, you can't delete.");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            visitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
