import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from './auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  userToLogin: LoginUserViewModel = {
    username: '',
    password: ''
  };
  invalidCredentials = false;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void { }

  handleLogin() {
    this.authService.authenticationService(this.userToLogin.username, this.userToLogin.password).subscribe(result => {
      this.invalidCredentials = false;
      this.router.navigate(['/admin-panel']);
    }, () => {
      this.invalidCredentials = true;
    });
  }
}

export interface LoginUserViewModel {
  username: string;
  password: string;
}
