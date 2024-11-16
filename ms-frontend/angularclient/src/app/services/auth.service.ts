import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token!: string;

  login() {
    this.token = 'token';
  }

  getToken(): string {
    return this.token;
  }

  constructor() { }
}
