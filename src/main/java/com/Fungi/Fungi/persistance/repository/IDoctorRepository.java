package com.Fungi.Fungi.persistance.repository;
import com.Fungi.Fungi.persistance.entity.Doctor;
import com.Fungi.Fungi.persistance.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {
    boolean existsBylicenseNumber(Integer licenseNumber);
    Doctor findByEmail(String email);

    @Query("SELECT p FROM Doctor d JOIN d.patients p WHERE d.id = :doctorId")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") Long doctorId);
}
