import {Component, OnInit} from '@angular/core';
import {AccountService} from '../service/account.service';
import {User} from '../model/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../login/login.component.scss', './register.component.scss']
})
export class RegisterComponent implements OnInit {

  userToRegister: UserRegisterViewModel = {
    login: '',
    password: '',
    confirmPassword: '',
    email: ''
  };

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
  }

  signUp() {
    const account = new User(this.userToRegister.login,  this.userToRegister.email, this.userToRegister.password);
    console.log(account);
    this.accountService.registerUser(account).subscribe(result => {
      console.log(result);
    }, error => {
      console.log(error);
    });
  }
}

export interface UserRegisterViewModel {
  login: string;
  password: string;
  confirmPassword: string;
  email: string;
}
