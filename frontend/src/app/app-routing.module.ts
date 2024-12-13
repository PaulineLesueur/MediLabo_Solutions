import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientsListComponent } from './patients-list/patients-list.component';
import { PatientDetailComponent } from './patient-detail/patient-detail.component';
import { PatientFormComponent } from './patient-form/patient-form.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { AuthGuard } from './guards/auth.guard';
import { NoteFormComponent } from './note-form/note-form.component';

const routes: Routes = [
  { path: 'login', component: LoginFormComponent },
  { path: 'patients-list', component: PatientsListComponent, canActivate: [AuthGuard] },
  { path: 'patient/:id', component: PatientDetailComponent, canActivate: [AuthGuard] },
  { path: 'patient/:id/update', component: PatientFormComponent, canActivate: [AuthGuard] },
  { path: 'patients-list/create-patient', component: PatientFormComponent, canActivate: [AuthGuard] },
  { path: 'patient/:id/add-note', component: NoteFormComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'patients-list', pathMatch: 'full' },
  { path: '**', redirectTo: 'patients-list' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
