package com.Fungi.Fungi.controller.data;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Integer idCard;
    private Integer phoneNumber;
    private LocalDate birthDate;
    private String specialty;
    private Long doctorId;

}
