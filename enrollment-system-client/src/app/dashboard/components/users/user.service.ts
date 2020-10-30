import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {IUser} from '../../../model/user.model';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private USER_URL = environment.basePath + '/users';
  private CHANGE_ROLE_URL = this.USER_URL + '/role';

  constructor(private httpClient: HttpClient) { }

  getAllUsers(): Observable<IUser[]> {
    return this.httpClient.get<IUser[]>(this.USER_URL);
  }

  changeRole(user: IUser): Observable<IUser> {
    return this.httpClient.patch<IUser>(this.CHANGE_ROLE_URL, user);
  }
}
