import {Injectable} from '@angular/core';
import {IUser} from '../user/model/user.model';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AuthService} from '../login/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  public registerUser(account: IUser): Observable<IUser> {
    return this.http.post(environment.basePath + '/register', account);
  }

  public getUser(): Observable<IUser> {
    return this.http.get(environment.basePath + '/user/' + this.authService.getLoggedInUserName());
  }
}
