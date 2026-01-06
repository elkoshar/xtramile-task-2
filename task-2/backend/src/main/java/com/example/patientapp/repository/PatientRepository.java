package com.example.patientapp.repository;

import com.example.patientapp.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "CAST(p.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Patient> search(@Param("keyword") String keyword, Pageable pageable);
}
