import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../services/patient.service';
import { Patient } from '../models/patient';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.sass']
})
export class PatientFormComponent implements OnInit {
  patientForm!: FormGroup;
  patient: Patient | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private patientService: PatientService,
    private router: Router 
  ) {}

  ngOnInit(): void {
    let id: string | null = this.route.snapshot.paramMap.get('id'); 
    if (id) {
      this.patientService.findById(+id).subscribe((data: Patient) => {
        console.log(data);
        this.patient = data;
        this.initializeForm(); 
      });
    } else {
      this.initializeForm(); 
    }
  }

  initializeForm() {
    this.patientForm = this.formBuilder.group({
      lastName: [this.patient?.lastName || '', [Validators.required, Validators.minLength(1)]],
      firstName: [this.patient?.firstName || '', [Validators.required, Validators.minLength(1)]],
      birthdate: [this.patient?.birthdate || '', Validators.required],
      gender: [this.patient?.gender || '', Validators.required],
      address: [this.patient?.address || ''],
      phone: [this.patient?.phone || '']
    });
  }

  onSubmit() {
    if (this.patientForm.valid) {
      const updatedPatient: Patient = {
        ...this.patient, 
        ...this.patientForm.value 
      };

      this.patientService.updatePatient(updatedPatient).subscribe(() => {
        this.router.navigate([`/patient/${updatedPatient.id}`]); 
      });
    }
  }
}
