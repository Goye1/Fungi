package com.Fungi.Fungi.service;

import com.Fungi.Fungi.exceptions.AlreadyExistsException;
import com.Fungi.Fungi.exceptions.ResourceNotFoundException;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.persistance.repository.IFormRepository;
import com.Fungi.Fungi.persistance.repository.IPatientRepository;
import com.Fungi.Fungi.persistance.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {


    @Autowired
    private ObjectMapper objectMapper;
    private final IPatientRepository patientRepository;

    private final IUserRepository userRepository;
    private final IFormRepository formRepository;

    public PatientService(IPatientRepository patientRepository, IFormRepository formRepository,IUserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.formRepository = formRepository;
        this.userRepository = userRepository;
    }

    public void addPatient(Patient patient) throws AlreadyExistsException {
            patientRepository.save(patient);
    }


    public Patient findById(Long id) throws ResourceNotFoundException {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient with the id: " + id + "not found"));
    }

    public Patient findByPhoneNumber(Integer phoneNumber) throws ResourceNotFoundException {
        Patient patient = patientRepository.findByPhoneNumber(phoneNumber);
        if(patient == null){
            throw new ResourceNotFoundException("User not found");
        }
        return patient;
    }

    public Patient findByEmail(String email) throws ResourceNotFoundException {
        Patient patient = patientRepository.findByEmail(email);
        if(patient == null){
            throw new ResourceNotFoundException("User not found");
        }
        return patient;
    }

    public void deleteUserByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        userRepository.deleteByEmail(email);
    }



}

