package com.Fungi.Fungi.service;
import com.Fungi.Fungi.controller.data.AuthenticationRequest;
import com.Fungi.Fungi.controller.data.AuthenticationResponse;
import com.Fungi.Fungi.controller.data.RegisterRequest;
import com.Fungi.Fungi.exceptions.AlreadyExistsException;
import com.Fungi.Fungi.exceptions.ResourceNotFoundException;
import com.Fungi.Fungi.persistance.entity.Doctor;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.persistance.entity.Role;
import com.Fungi.Fungi.persistance.entity.User;
import com.Fungi.Fungi.persistance.repository.IDoctorRepository;
import com.Fungi.Fungi.persistance.repository.IPatientRepository;
import com.Fungi.Fungi.persistance.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;

    private final IDoctorRepository doctorRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final IPatientRepository patientRepository;

    public AuthenticationResponse login(AuthenticationRequest request) throws ResourceNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user  = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        Patient patient = null;
        Doctor doctor = null;
        if(user.getRole() == Role.USER){
            patient = patientService.findByEmail(request.getEmail());
        }else{
            doctor = doctorService.findByEmail(request.getEmail());
        }
        return AuthenticationResponse.builder()
                .patient(patient)
                .doctor(doctor)
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse registerPatient(RegisterRequest request) throws AlreadyExistsException {
        if(userRepository.existsByEmail(request.getEmail()) || patientRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AlreadyExistsException("A user is already registered with that email por phone number.");
        }
        Patient patient = new Patient();
        patient.setName(request.getFirstname());
        patient.setSurname(request.getLastname());
        patient.setIdCard(request.getIdCard());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setBirthDate(request.getBirthDate());
        patientService.addPatient(patient);
        doctorService.addPatientToDoctor(request.getDoctorId(),patient);
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) throws AlreadyExistsException, ResourceNotFoundException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AlreadyExistsException("A user is already registered with that email.");
        }
        Doctor doctor = new Doctor();
        doctor.setName(request.getFirstname());
        doctor.setSurname(request.getLastname());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setEmail(request.getEmail());
        doctor.setLicenseNumber(request.getIdCard());
        doctorService.addDoctor(doctor);
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
