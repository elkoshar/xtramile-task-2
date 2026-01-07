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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

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

     @Test
    void updatePatient_Success() {
        Long patientId = 1L;
        Patient existingPatient = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");
        Patient patientDetails = new Patient(null, "Jane", "Smith", LocalDate.of(1985, 5, 15), "Female", "0987654321", "456 Ave", "City", "NSW", "5678");
        Patient updatedPatient = new Patient(patientId, "Jane", "Smith", LocalDate.of(1985, 5, 15), "Female", "0987654321", "456 Ave", "City", "NSW", "5678");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient result = patientService.updatePatient(patientId, patientDetails);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals(LocalDate.of(1985, 5, 15), result.getDateOfBirth());
        assertEquals("Female", result.getGender());
        assertEquals("0987654321", result.getPhoneNumber());
        assertEquals("456 Ave", result.getAddress());
        assertEquals("City", result.getSuburb());
        assertEquals("NSW", result.getState());
        assertEquals("5678", result.getPostcode());

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void updatePatient_NotFound() {
        Long patientId = 999L;
        Patient patientDetails = new Patient(null, "Jane", "Smith", LocalDate.of(1985, 5, 15), "Female", "0987654321", "456 Ave", "City", "NSW", "5678");

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            patientService.updatePatient(patientId, patientDetails));

        assertEquals("Patient not found with id: " + patientId, exception.getMessage());
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void deletePatient_Success() {
        Long patientId = 1L;
        Patient existingPatient = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "123 St", "Suburb", "State", "1234");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        doNothing().when(patientRepository).delete(existingPatient);

        assertDoesNotThrow(() -> patientService.deletePatient(patientId));

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).delete(existingPatient);
    }

    @Test
    void deletePatient_NotFound() {
        Long patientId = 999L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            patientService.deletePatient(patientId));

        assertEquals("Patient not found with id: " + patientId, exception.getMessage());
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).delete(any(Patient.class));
    }
}
