import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private patientsApi: string;

  constructor(private http: HttpClient) {
    this.patientsApi = 'http://localhost:8081/patients';
   }

   public findAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.patientsApi);
   }
}
