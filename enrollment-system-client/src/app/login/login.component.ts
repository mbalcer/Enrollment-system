import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from './auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userToLogin: LoginUserViewModel = {
    username: '',
    password: ''
  };
  errorMessage = 'Invalid Credentials';
  successMessage: string;
  invalidLogin = false;
  loginSuccess = false;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void { }

  handleLogin() {
    this.authService.authenticationService(this.userToLogin.username, this.userToLogin.password).subscribe(result => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful';
      this.router.navigate(['/admin-panel']);
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });
  }
}

export interface LoginUserViewModel {
  username: string;
  password: string;
}
