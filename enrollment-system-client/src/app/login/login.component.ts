import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from './auth/auth.service';
import {FormMessage} from '../model/form-message.model';
import {TypeMessage} from '../model/enumeration/type-message.enum';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public TypeMessage = TypeMessage;
  messageLogin: FormMessage = new FormMessage();
  userToLogin: LoginUserViewModel = {
    username: '',
    password: ''
  };

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void { }

  handleLogin() {
    this.authService.authenticationService(this.userToLogin.username, this.userToLogin.password).subscribe(result => {
      this.router.navigate(['/admin-panel']);
    }, errorResponse => {
      this.messageLogin = {
        type: TypeMessage.ERROR,
        // message: errorResponse.error.message
        message: 'Invalid credentials'
      };
    });
  }
}

export interface LoginUserViewModel {
  username: string;
  password: string;
}
