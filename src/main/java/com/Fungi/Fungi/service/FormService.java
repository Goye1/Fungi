package com.Fungi.Fungi.service;

import com.Fungi.Fungi.persistance.entity.Doctor;
import com.Fungi.Fungi.persistance.entity.Form;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.persistance.entity.QAR;
import com.Fungi.Fungi.persistance.repository.IDoctorRepository;
import com.Fungi.Fungi.persistance.repository.IFormRepository;
import com.Fungi.Fungi.persistance.repository.IPatientRepository;
import com.Fungi.Fungi.persistance.repository.IQARRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FormService {
    private final IFormRepository formRepository;

    private final IPatientRepository patientRepository;
    private final IDoctorRepository doctorRepository;
    private final IQARRepository qarRepository;

    public FormService(IFormRepository formRepository, IPatientRepository patientRepository, IDoctorRepository doctorRepository, IQARRepository qarRepository) {
        this.formRepository = formRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.qarRepository = qarRepository;
    }


    public Form respondToForm(Long formId, Map<Long, String> responses) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        responses.forEach((qarId, response) -> {
            QAR qar = qarRepository.findById(qarId)
                    .orElseThrow(() -> new EntityNotFoundException("QAR not found"));
            Set<String> patientResponses = qar.getPatientResponses();
            patientResponses.add(response);
            qarRepository.updatePatientResponses(qarId, patientResponses);
        });
        return form;
    }

    public List<QAR> reviewFormResponses(Long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        List<QAR> qars = qarRepository.findByFormId(formId);
        // Agrega un registro de depuración para verificar los resultados de la consulta
        System.out.println("QARs encontrados para el formulario con ID: " + formId + ": " + qars);
        return qars;
    }

    public List<Form> getFormsForPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        return formRepository.findByPatientId(patientId);
    }


    public Form createFormForPatient(Long doctorId, Long patientId, List<QAR> qars) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        if (!doctor.getPatients().contains(patient)) {
            throw new IllegalArgumentException("The doctor does not have the patient in their list");
        }

        Form form = new Form();
        form.setPatient(patient);
        form.setCreationDate(LocalDate.now());

        // Guarda el formulario en la base de datos antes de asociar los QARs
        Form savedForm = formRepository.save(form);

        // Elimina la colección existente y agrega los nuevos QARs
        savedForm.getQars().clear();
        savedForm.getQars().addAll(qars.stream().peek(qar -> qar.setForm(savedForm)).collect(Collectors.toSet()));

        // Actualiza la entidad Form para que JPA pueda procesar los cambios
        formRepository.save(savedForm);

        return savedForm;
    }

    public void patientRespondsToForm(Long patientId, Long formId, Map<Long, String> responses) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));

        if (!form.getPatient().equals(patient)) {
            throw new IllegalArgumentException("The patient does not have access to this form");
        }

        responses.forEach((qarId, response) -> {
            QAR qar = qarRepository.findById(qarId)
                    .orElseThrow(() -> new EntityNotFoundException("QAR not found"));
            if (!qar.getForm().equals(form)) {
                throw new IllegalArgumentException("The QAR does not belong to this form");
            }
            qar.getPatientResponses().add(response);
            qarRepository.save(qar);
        });
    }


}
