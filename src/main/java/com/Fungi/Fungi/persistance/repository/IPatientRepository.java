package com.Fungi.Fungi.persistance.repository;
import com.Fungi.Fungi.persistance.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient,Long> {
    boolean existsByidCard(Integer idCard);
    Patient findByIdCard(Long id);
    List<Patient> findByDoctorId(Long doctorId);
    Patient findByEmail(String email);

    Patient findByPhoneNumber(Integer phoneNumber);

    Boolean existsByPhoneNumber(Integer phoneNumber);
}
