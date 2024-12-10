import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DiabeteReportService {
  private apiUrl = 'http://localhost:8083';

  constructor(private http: HttpClient) { }

  getDiabetesRisk(patId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/diabetes-report/${patId}`);
  }
}
