import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {
  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  onLogout() {
    this.authService.logout(); // Déconnexion
    this.router.navigate(['/login']); // Redirection vers la page de login
  }

  get username(): String | null {
    return this.authService.getUsername();
  }
}