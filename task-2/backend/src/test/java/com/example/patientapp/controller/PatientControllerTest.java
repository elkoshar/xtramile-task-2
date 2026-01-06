package com.example.patientapp.controller;

import com.example.patientapp.model.Patient;
import com.example.patientapp.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    void getPatients() {
        Patient patient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        Page<Patient> page = new PageImpl<>(Collections.singletonList(patient));
        
        when(patientService.getPatients(any(), any())).thenReturn(page);

        Page<Patient> result = patientController.getPatients(null, Pageable.unpaged());
        
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }

    @Test
    void getPatientById() {
        Patient patient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void createPatient() {
        Patient patient = new Patient(null, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        Patient savedPatient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");

        when(patientService.createPatient(patient)).thenReturn(savedPatient);

        Patient result = patientController.createPatient(patient);

        assertEquals(1L, result.getId());
    }
}
