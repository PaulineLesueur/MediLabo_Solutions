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

   public findAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.patientsApi);
   }

   public findById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.patientDetail}/${id}`)
   }

   public updatePatient(patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.patientDetail}/${patient.id}/update`, patient);
   }
}
