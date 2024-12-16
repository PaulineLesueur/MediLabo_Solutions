import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/login';

  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService
  ) {}

  login(username: string, password: string): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Basic ${btoa(`${username}:${password}`)}`,
    });

    return this.http.post(this.apiUrl, null, {
      headers,
      responseType: 'text',
    });
  }

  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
    console.log('Token saved:', token);
  }

  logout(): void {
    localStorage.removeItem('auth_token');
    console.log('Token deleted');
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('auth_token');
    return token != null && !this.jwtHelper.isTokenExpired(token);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  getRoles(): string[] {
    const token = localStorage.getItem('auth_token');
    if (token && !this.jwtHelper.isTokenExpired(token)) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      return decodedToken?.roles || []; 
    }
    return [];
  }

  hasRole(role: string): boolean {
    const roles = this.getRoles();
    return roles.includes(role);
  }

  getUsername(): String | null {
    const token = this.getToken();
    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token);  
      return decodedToken?.sub ? decodedToken.sub : null; 
    }
    return null;
  }
}