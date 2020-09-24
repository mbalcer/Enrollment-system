import {Injectable} from '@angular/core';
import {IUser} from '../model/user.model';
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
}
