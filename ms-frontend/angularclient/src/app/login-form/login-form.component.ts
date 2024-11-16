import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.sass']
})
export class LoginFormComponent implements OnInit {
  constructor(private auth: AuthService, private router: Router) {   }

  ngOnInit(): void {  }

  onLogin() {
    this.auth.login();
    this.router.navigateByUrl('/patients-list');
  }
}
