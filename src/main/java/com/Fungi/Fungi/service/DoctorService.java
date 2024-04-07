package com.Fungi.Fungi.service;
import com.Fungi.Fungi.exceptions.ResourceNotFoundException;
import com.Fungi.Fungi.persistance.entity.Doctor;
import com.Fungi.Fungi.persistance.entity.Form;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.persistance.entity.QAR;
import com.Fungi.Fungi.persistance.repository.IDoctorRepository;
import com.Fungi.Fungi.persistance.repository.IFormRepository;
import com.Fungi.Fungi.persistance.repository.IQARRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DoctorService {

   private final IDoctorRepository doctorRepository;

   private final IFormRepository formRepository;

   private final IQARRepository qarRepository;

    @Autowired
    public DoctorService(IDoctorRepository dentistRepository, IFormRepository formRepository, IQARRepository qarRepository) {
        this.doctorRepository = dentistRepository;
        this.formRepository = formRepository;
        this.qarRepository = qarRepository;
    }

    public List<Doctor> listDentists() throws ResourceNotFoundException {
        return doctorRepository.findAll();
    }

    public Doctor addDoctor(Doctor doctor) throws ResourceNotFoundException {
        if (doctorRepository.existsBylicenseNumber(doctor.getLicenseNumber())) {
            throw new ResourceNotFoundException("A dentist with " + doctor.getLicenseNumber() + " license number already exists");
        }
        doctorRepository.save(doctor);
        return doctor;
    }
    public Doctor findByEmail(String email) throws ResourceNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(email);
        if(doctor == null){
            throw new ResourceNotFoundException("User not found");
        }
        return doctor;
    }

    public List<QAR> doctorReviewsFormResponses(Long doctorId, Long formId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        if (!doctor.getPatients().contains(form.getPatient())) {
            throw new IllegalArgumentException("The doctor does not have the patient in their list");
        }

        return qarRepository.findByFormId(formId);
    }


    @Transactional
    public void addPatientToDoctor(Long doctorId, Patient patient) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        if (patient.getDoctor() != null && !patient.getDoctor().getId().equals(doctorId)) {
            throw new IllegalArgumentException("The patient is already assigned to another doctor");
        }
        doctor.getPatients().add(patient);
        patient.setDoctor(doctor);

        doctorRepository.save(doctor);
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        List<Patient> patients = doctorRepository.findPatientsByDoctorId(doctorId);
        if (patients.isEmpty()) {
            throw new EntityNotFoundException("No patients found for doctor with id: " + doctorId);
        }
        return patients;
    }

    public String removePatientFromDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId));
        Patient patient = doctor.getPatients().stream()
                .filter(p -> p.getId().equals(patientId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));
        doctor.getPatients().remove(patient);
        patient.setDoctor(null);
        doctorRepository.save(doctor);
        return "Patient " + patient.getName() + " " + patient.getSurname() + " eliminated";
    }
}





