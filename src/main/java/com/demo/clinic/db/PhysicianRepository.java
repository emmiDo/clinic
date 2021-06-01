package com.demo.clinic.db;

import com.demo.clinic.beans.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicianRepository extends JpaRepository<Physician, Integer> {

}
