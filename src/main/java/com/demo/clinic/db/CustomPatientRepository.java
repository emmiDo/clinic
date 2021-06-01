package com.demo.clinic.db;

import com.demo.clinic.beans.Patient;

import java.util.List;

public interface CustomPatientRepository {

    List<Patient> query(DynamicQuery dynamicQuery);

}
