package com.example.patientapp.config;

import com.example.patientapp.model.Patient;
import com.example.patientapp.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(PatientRepository patientRepository) {
        return args -> {
            if (patientRepository.count() == 0) {
                Patient p1 = new Patient(null, "John", "Doe", LocalDate.of(1985, 5, 20), "Male", "5557411", "123 George St", "Sydney", "NSW", "2000");
                Patient p2 = new Patient(null, "Jane", "Smith", LocalDate.of(1990, 8, 15), "Female", "5557783", "456 Queen St", "Melbourne", "VIC", "3000");
 
                patientRepository.saveAll(Arrays.asList(p1, p2));
                System.out.println("Data Initialized: 2 patients created.");
            }
        };
    }
}
