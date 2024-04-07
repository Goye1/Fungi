package com.Fungi.Fungi.controller;
import com.Fungi.Fungi.controller.data.AuthenticationRequest;
import com.Fungi.Fungi.controller.data.AuthenticationResponse;
import com.Fungi.Fungi.controller.data.RegisterRequest;
import com.Fungi.Fungi.exceptions.AlreadyExistsException;
import com.Fungi.Fungi.exceptions.ResourceNotFoundException;
import com.Fungi.Fungi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/landing-page")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> registerPatient(@RequestBody RegisterRequest request) throws AlreadyExistsException {
        return ResponseEntity.ok(authenticationService.registerPatient(request));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/register-doctor")
    ResponseEntity<AuthenticationResponse> registerDoctor(@RequestBody RegisterRequest request) throws AlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }


}
