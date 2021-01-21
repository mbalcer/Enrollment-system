import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../user/auth/auth.service';
import {FormMessage} from '../model/form-message.model';
import {TypeMessage} from '../model/enumeration/type-message.enum';
import {TokenStorageService} from '../user/auth/token-storage.service';

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

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit(): void { }

  handleLogin() {
    this.authService.login(this.userToLogin).subscribe(result => {
      this.tokenStorage.saveToken(result.jwt);
      this.tokenStorage.saveUser(result);
      this.tokenStorage.saveActiveRole(result.role[0]);
      this.router.navigate(['/dashboard']);
    }, errorResponse => {
      this.userToLogin.password = '';
      this.messageLogin.type = TypeMessage.ERROR;
      if(errorResponse.error.message.length == 0) {
        this.messageLogin.message = 'Invalid credentials';
      } else {
        this.messageLogin.message = errorResponse.error.message;
      };
    });
  }
}

export interface LoginUserViewModel {
  username: string;
  password: string;
}
