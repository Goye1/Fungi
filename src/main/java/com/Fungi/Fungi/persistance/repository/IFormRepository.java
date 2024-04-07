package com.Fungi.Fungi.persistance.repository;

import com.Fungi.Fungi.persistance.entity.Form;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IFormRepository extends JpaRepository<Form, Long> {
    @EntityGraph(attributePaths = {"qars"})
    List<Form> findByPatientId(Long patientId);
}