package net.samge.model;

import java.sql.Timestamp;

public class PatientRegister {
    private String regId;
    private String patientName;
    private Timestamp regDateTime;
    private String regCat;

    public String getRegId() {
        return regId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Timestamp getRegDateTime() {
        return regDateTime;
    }

    public String getRegCat() {
        return regCat;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setRegDateTime(Timestamp regDateTime) {
        this.regDateTime = regDateTime;
    }

    public void setRegCat(String regCat) {
        this.regCat = regCat;
    }

    public PatientRegister(String regId, String patientName, Timestamp regDateTime, String regCat) {
        this.regId = regId;
        this.patientName = patientName;
        this.regDateTime = regDateTime;
        this.regCat = regCat;
    }

    public PatientRegister() {
    }
}
