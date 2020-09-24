import {Component, OnInit} from '@angular/core';
import {AccountService} from '../service/account.service';
import {User} from '../model/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../login/login.component.scss', './register.component.scss']
})
export class RegisterComponent implements OnInit {

  errorRegister: ErrorForm = {
    isError: false,
    message: ''
  };

  userToRegister: UserRegisterViewModel;

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.initUserToRegister();
  }

  initUserToRegister() {
    this.userToRegister = {
      login: '',
      password: '',
      confirmPassword: '',
      email: ''
    };
  }

  signUp() {
    const account = new User(this.userToRegister.login,  this.userToRegister.email, this.userToRegister.password);
    this.accountService.registerUser(account).subscribe(result => {
      this.errorRegister =  {
        isError: false,
        message: 'You have been successfully registered'
      };
      this.initUserToRegister();
    }, errorResponse => {
      this.errorRegister = {
        isError: true,
        message: errorResponse.error.message
      };
    });
  }
}

export interface UserRegisterViewModel {
  login: string;
  password: string;
  confirmPassword: string;
  email: string;
}

export interface ErrorForm {
  isError: boolean;
  message?: string;
}
