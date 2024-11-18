import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientsListComponent } from './patients-list/patients-list.component';
import { PatientDetailComponent } from './patient-detail/patient-detail.component';
import { PatientFormComponent } from './patient-form/patient-form.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { AuthOrganizerGuard } from './guards/auth-organizer.guard';
import { AuthPractitionerGuard } from './guards/auth-practitioner.guard';

const routes: Routes = [
  { path: 'login', component: LoginFormComponent },
  { path: 'patients-list', component: PatientsListComponent, canActivate: [AuthOrganizerGuard] },
  { path: 'patient/:id', component: PatientDetailComponent, canActivate: [AuthOrganizerGuard] },
  { path: 'patient/:id/update', component: PatientFormComponent, canActivate: [AuthOrganizerGuard] },
  { path: 'patients-list/create-patient', component: PatientFormComponent, canActivate: [AuthOrganizerGuard] },
  { path: '', redirectTo: 'patients-list', pathMatch: 'full' },
  { path: '**', redirectTo: 'patients-list' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
