package com.Fungi.Fungi.controller.data;


import com.Fungi.Fungi.persistance.entity.Doctor;
import com.Fungi.Fungi.persistance.entity.Patient;
import com.Fungi.Fungi.persistance.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private Patient patient;
    private Doctor doctor;
    private Role role;
}
