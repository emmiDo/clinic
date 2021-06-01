package com.demo.clinic.db;

import com.demo.clinic.beans.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository  extends JpaRepository<Visit, Long>{



}
