package com.Fungi.Fungi.controller;

import com.Fungi.Fungi.exceptions.AlreadyExistsException;
import com.Fungi.Fungi.exceptions.ResourceNotFoundException;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @CrossOrigin(origins = "*")
    @PostMapping("/get-phone")
    public ResponseEntity<Patient> getByPhoneNumber(@RequestParam Integer phoneNumber) throws AlreadyExistsException, ResourceNotFoundException {
        return new ResponseEntity<>(patientService.findByPhoneNumber(phoneNumber),HttpStatus.OK);
    }

}
