package com.Fungi.Fungi.controller;

import com.Fungi.Fungi.controller.data.FormCreationRequest;
import com.Fungi.Fungi.exceptions.AlreadyExistsException;
import com.Fungi.Fungi.persistance.entity.Form;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.persistance.entity.QAR;
import com.Fungi.Fungi.service.DoctorService;
import com.Fungi.Fungi.service.FormService;
import com.Fungi.Fungi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {


    private final FormService formService;

    private final PatientService patientService;

    private final DoctorService doctorService;

    @CrossOrigin(origins = "*")
    @PostMapping("/createForm")
    public ResponseEntity<Form> createFormForPatient(@RequestBody FormCreationRequest formCreationRequest) throws AlreadyExistsException {
        return new ResponseEntity<>(formService.createFormForPatient(formCreationRequest.getDoctorId(), formCreationRequest.getPatientId(), formCreationRequest.getQars()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/respondForm")
    ResponseEntity<Form> createFormForPatient(@RequestBody Long formId, Map<Long, String> responses) throws AlreadyExistsException {
        return new ResponseEntity<>(formService.respondToForm(formId,responses), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getForms")
    public ResponseEntity<List<Form>> getFormsFromPatient(@RequestParam Long patientId) throws AlreadyExistsException {
        List<Form> formList = formService.getFormsForPatient(patientId);
        return new ResponseEntity<>(formList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getQAR")
    public ResponseEntity<List<QAR>> reviewFormResponses(@RequestParam Long formId) throws AlreadyExistsException {
        List<QAR> formList = formService.reviewFormResponses(formId);
        return new ResponseEntity<>(formList, HttpStatus.OK);
    }




}
