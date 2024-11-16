import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html'
})
export class LogoutComponent implements OnInit {
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {  }

  onLogout() {
    this.router.navigateByUrl('/login');
  }
}
