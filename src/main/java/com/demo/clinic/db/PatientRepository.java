package com.demo.clinic.db;

import com.demo.clinic.beans.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long>, CustomPatientRepository {

    List<Patient> findByNameLike(String title);

}
