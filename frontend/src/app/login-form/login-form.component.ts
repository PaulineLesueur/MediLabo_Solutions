import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.sass'],
})
export class LoginFormComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    this.authService.login(this.username, this.password).subscribe(
      (token) => {
        console.log('Login successful!', token);
        this.authService.saveToken(token); // Sauvegarde le token
        this.router.navigate(['/patients-list']); // Redirection vers la liste des patients
      },
      (error) => {
        console.error('Login failed', error);
        this.errorMessage = 'Invalid username or password. Please try again.';
      }
    );
  }
}