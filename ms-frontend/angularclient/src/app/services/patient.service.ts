import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private patientsApi: string;
  private patientDetail: string

  constructor(private http: HttpClient) {
    this.patientsApi = 'http://localhost:8081/patients';
    this.patientDetail = 'http://localhost:8081/patient'
   }

   public findAll(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.patientsApi}?page=${page}&size=${size}`);
   }

   public findById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.patientDetail}/${id}`)
   }

   public updatePatient(patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.patientDetail}/${patient.id}/update`, patient);
   }

   public createPatient(newPatient: Patient) {
    return this.http.post<Patient>(`${this.patientsApi}/create`, newPatient);
   }
}
