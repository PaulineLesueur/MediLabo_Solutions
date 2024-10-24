import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientsListComponent } from './patients-list/patients-list.component';
import { PatientDetailComponent } from './patient-detail/patient-detail.component';
import { PatientFormComponent } from './patient-form/patient-form.component';

const routes: Routes = [
  { path: 'patients-list', component: PatientsListComponent },
  { path: 'patient/:id', component: PatientDetailComponent},
  { path: 'patient/:id/update', component: PatientFormComponent},
  { path: '', redirectTo: 'patients-list', pathMatch: 'full' },
  { path: '**', redirectTo: 'patients-list' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
