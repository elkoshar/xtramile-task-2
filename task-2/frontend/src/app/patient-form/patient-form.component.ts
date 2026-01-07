import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // 1. Import ChangeDetectorRef
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../service/patient.service';
import { Patient } from '../model/patient.model';

@Component({
  selector: 'app-patient-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent implements OnInit {
  
  patient: Patient = {
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    phoneNumber: '',
    address: '',
    suburb: '',
    state: '',
    postcode: ''
  };
  
  isEditMode: boolean = false;

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    
    if (id) {
      this.isEditMode = true;
      this.patientService.getPatient(+id).subscribe({
        next: (data) => {
          this.patient = data;
          
          this.cdr.detectChanges(); 
        },
        error: (err) => {
          console.error('Error loading patient:', err);
        }
      });
    }
  }

  onSubmit(): void {
    if (this.isEditMode && this.patient.id) {
      this.patientService.updatePatient(this.patient.id, this.patient).subscribe({
        next: () => {
          alert('Patient updated successfully');
          this.router.navigate(['/']);
        },
        error: (err) => {
          console.error('Error updating patient:', err);
        }
      });
    } else {
      this.patientService.createPatient(this.patient).subscribe({
        next: () => {
          alert('Patient created successfully');
          this.router.navigate(['/']);
        },
        error: (err) => {
          console.error('Error creating patient:', err);
        }
      });
    }
  }
}