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
  currentPage: number = 0;
  pageSize: number= 5;
  totalPatients: number = 0;
  pagesArray: number[] = [];

  constructor(private patientService: PatientService) {
  }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(): void {
    this.patientService.findAll(this.currentPage, this.pageSize).subscribe(data => {
      this.patients = data.content;
      this.totalPatients = data.totalElements;
      this.pagesArray = Array.from({ length: this.getTotalPages()}, (_, i) => i);
    });
  }

  goToPage(page: number): void {
    if(page >= 0 && page < this.getTotalPages()) {
      this.currentPage = page;
      this.loadPatients();
    }
  }

  getTotalPages(): number {
    return Math.ceil(this.totalPatients / this.pageSize);
  }
}
