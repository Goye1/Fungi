package com.Fungi.Fungi.persistance.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table
public class QAR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ElementCollection
    private Set<String> possibleAnswers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @ElementCollection
    private Set<String> patientResponses = new HashSet<>();
}
