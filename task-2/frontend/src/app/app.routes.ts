import { Routes } from '@angular/router';
import { PatientListComponent } from './patient-list/patient-list.component';
import { PatientFormComponent } from './patient-form/patient-form.component';

export const routes: Routes = [
  { path: '', component: PatientListComponent },
  { path: 'add', component: PatientFormComponent },
  { path: 'edit/:id', component: PatientFormComponent }
];
