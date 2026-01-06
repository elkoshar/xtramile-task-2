package com.example.patientapp.service;

import com.example.patientapp.model.Patient;
import com.example.patientapp.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void getPatients() {
        Patient patient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        Page<Patient> page = new PageImpl<>(Collections.singletonList(patient));
        Pageable pageable = PageRequest.of(0, 10);

        when(patientRepository.search(any(), eq(pageable))).thenReturn(page);

        Page<Patient> result = patientService.getPatients("John", pageable);

        assertEquals(1, result.getTotalElements());
        verify(patientRepository, times(1)).search("John", pageable);
    }

    @Test
    void getPatientById() {
        Patient patient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.getPatientById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void createPatient() {
        Patient patient = new Patient(null, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        Patient savedPatient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");

        when(patientRepository.save(patient)).thenReturn(savedPatient);

        Patient result = patientService.createPatient(patient);

        assertNotNull(result.getId());
        assertEquals("John", result.getFirstName());
    }
}
