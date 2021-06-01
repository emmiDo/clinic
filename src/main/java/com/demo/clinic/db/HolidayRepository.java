package com.demo.clinic.db;

import com.demo.clinic.beans.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    List<Holiday> findByHoliday(Date date);

    List<Holiday> findByCreatedBy(String name);
}
