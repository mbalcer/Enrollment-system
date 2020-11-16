import {Component, OnInit} from '@angular/core';
import {User} from '../user/model/user.model';
import {FormMessage} from '../model/form-message.model';
import {TypeMessage} from '../model/enumeration/type-message.enum';
import {AuthService} from '../user/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../login/login.component.scss', './register.component.scss']
})
export class RegisterComponent implements OnInit {

  public TypeMessageEnum = TypeMessage;
  messageRegister: FormMessage = new FormMessage();
  userToRegister: UserRegisterViewModel;

  constructor(private authService: AuthService) {
  }

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

  signUp(form) {
    const account = new User(this.userToRegister.login, this.userToRegister.email, this.userToRegister.password);
    this.authService.register(account).subscribe(result => {
      this.messageRegister = {
        type: TypeMessage.SUCCESS,
        message: 'You have been successfully registered'
      };
      this.initUserToRegister();
      form.submitted = false;
    }, errorResponse => {
      this.messageRegister = {
        type: TypeMessage.ERROR,
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
