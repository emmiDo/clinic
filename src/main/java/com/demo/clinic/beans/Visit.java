package com.demo.clinic.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Date visitTime;

    @Column(nullable = false)
    private int physicianId;

    @Column(nullable = false)
    private long patientId;
    private String reason;
    private Date createdTime;
    private Date modifiedTime;
    private String createdBy;
    private String modifiedBy;

    public Visit () {}

    public Visit(Date visitTime,
                 int physicianId,
                 long patientId,
                 String reason,
                 Date createdTime,
                 String createdBy) {
        this.visitTime = visitTime;
        this.physicianId = physicianId;
        this.patientId = patientId;
        this.reason = reason;
        this.createdTime = createdTime;
        this.createdBy = createdBy;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
