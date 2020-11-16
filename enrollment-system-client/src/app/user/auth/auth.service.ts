import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private AUTH_URL = environment.basePath + '/auth';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  login(credentials): Observable<any> {
    return this.http.post(this.AUTH_URL + '/login', {
      username: credentials.username,
      password: credentials.password
    }, this.httpOptions);
  }

  register(user): Observable<any> {
    return this.http.post(this.AUTH_URL + '/register', {
      username: user.username,
      email: user.email,
      password: user.password
    }, this.httpOptions);
  }
}
