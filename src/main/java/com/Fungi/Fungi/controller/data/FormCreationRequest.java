package com.Fungi.Fungi.controller.data;

import com.Fungi.Fungi.persistance.entity.QAR;

import java.util.List;

public class FormCreationRequest {
    private Long doctorId;
    private Long patientId;
    private List<QAR> qars;

    // Getters y setters
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public List<QAR> getQars() {
        return qars;
    }

    public void setQars(List<QAR> qars) {
        this.qars = qars;
    }
}
