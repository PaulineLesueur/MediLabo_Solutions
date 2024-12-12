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

  // Connexion de l'utilisateur avec un username et un mot de passe
  login(username: string, password: string): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Basic ${btoa(`${username}:${password}`)}`,
    });

    return this.http.post(this.apiUrl, null, {
      headers,
      responseType: 'text',
    });
  }

  // Sauvegarde du token dans le localStorage
  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
    console.log('Token saved:', token);
  }

  // Suppression du token lors de la déconnexion
  logout(): void {
    localStorage.removeItem('auth_token');
    console.log('Token deleted');
  }

  // Vérifie si l'utilisateur est authentifié
  isAuthenticated(): boolean {
    const token = localStorage.getItem('auth_token');
    return token != null && !this.jwtHelper.isTokenExpired(token);
  }

  // Récupère le token depuis le localStorage
  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }
}