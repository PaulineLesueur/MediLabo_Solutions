import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private patientsApi: string;
  private patientDetail: string;
  private api: string;

  constructor(private http: HttpClient) {
    this.api = 'http://localhost:8080';
   }

   public findAll(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.api}/patients?page=${page}&size=${size}`);
   }

   public findById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.api}/patient/${id}`)
   }

   public updatePatient(patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.api}/patient/${patient.id}/update`, patient);
   }

   public createPatient(newPatient: Patient) {
    return this.http.post<Patient>(`${this.api}/patients/create`, newPatient);
   }
}
