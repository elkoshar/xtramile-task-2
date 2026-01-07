import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PatientService } from '../service/patient.service';
import { Patient } from '../model/patient.model';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {
  patients: Patient[] = [];
  keyword: string = '';
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;

  constructor(
    private patientService: PatientService,
    private cdr: ChangeDetectorRef // Inject ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.onSearch();
  }

  loadPatients(): void {
  
    this.patientService.getPatients(this.keyword, this.page, this.size)
      .subscribe({
        next: (data) => {
          
          this.patients = data.content;
          this.totalPages = data.totalPages;

          this.cdr.detectChanges();
        },
        error: (e) => {
          console.error('ERROR', e);
        }
      });
  }

  onSearch(): void {
    this.page = 0;
    this.loadPatients();
  }

  onPageChange(newPage: number): void {
    this.page = newPage;
    this.loadPatients();
  }

  deletePatient(id: number): void {
    if (confirm('Are you sure you want to delete this patient?')) {
      this.patientService.deletePatient(id).subscribe({
        next: () => {
          alert('Patient deleted successfully');
          this.loadPatients();
        },
        error: (err) => {
          console.error('Error deleting patient:', err);
          alert('Failed to delete patient');
        }
      });
    }
  }
}