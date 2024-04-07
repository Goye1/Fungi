package com.Fungi.Fungi.controller;

import com.Fungi.Fungi.controller.data.FormCreationRequest;
import com.Fungi.Fungi.exceptions.AlreadyExistsException;
import com.Fungi.Fungi.persistance.entity.Form;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medic")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @CrossOrigin(origins = "*")
    @GetMapping("/get-patients")
    public ResponseEntity<List<Patient>> getAllPatients(@RequestParam Long doctorId ) throws AlreadyExistsException {
        return new ResponseEntity<>(doctorService.getPatientsByDoctorId(doctorId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete-patient")
    public ResponseEntity<String> deletePatient(@RequestParam Long patientID, Long doctorId  ) throws AlreadyExistsException {
        return new ResponseEntity<>(doctorService.removePatientFromDoctor(patientID,doctorId), HttpStatus.OK);
    }


}
