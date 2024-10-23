import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Patient } from '../models/patient';

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.sass']
})
export class PatientsListComponent implements OnInit {
  patients: Patient[] = [];

  constructor(private patientService: PatientService) {
  }

  ngOnInit(): void {
    this.loadPatients();
  }

  // Méthode pour charger les patients depuis l'API
  loadPatients(): void {
    this.patientService.findAll().subscribe(data => {
      this.patients = data;
    });
  }
}
