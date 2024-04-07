package com.Fungi.Fungi.persistance.repository;

import com.Fungi.Fungi.persistance.entity.QAR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IQARRepository extends JpaRepository<QAR,Long> {
    @Query("SELECT q FROM QAR q WHERE q.form.id = :formId")
    List<QAR> findByFormId(@Param("formId") Long formId);

    @Modifying
    @Query("UPDATE QAR q SET q.patientResponses = :responses WHERE q.id = :qarId")
    void updatePatientResponses(@Param("qarId") Long qarId, @Param("responses") Set<String> responses);
}
