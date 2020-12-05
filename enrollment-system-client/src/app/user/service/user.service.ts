import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {IUser} from '../model/user.model';
import {HttpClient} from '@angular/common/http';
import {TokenStorageService} from '../auth/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private USER_URL = environment.basePath + '/users';
  private CHANGE_ROLE_URL = this.USER_URL + '/role';

  constructor(private httpClient: HttpClient, private tokenStorageService: TokenStorageService) { }

  getAllUsers(): Observable<IUser[]> {
    return this.httpClient.get<IUser[]>(this.USER_URL);
  }

  getUser(): Observable<IUser> {
    return this.httpClient.get(this.USER_URL + '/' + this.tokenStorageService.getUser().username);
  }

  getUserByUsername(username: string): Observable<IUser> {
    return this.httpClient.get<IUser>(this.USER_URL + '/' + username);
  }

  addRoleToUser(username: string, role: string): Observable<IUser> {
    return this.httpClient.put<IUser>(this.CHANGE_ROLE_URL + '/add/' + username, this.getJsonForRoleResponseBody(role));
  }

  removeRoleFromUser(username: string, role: string): Observable<IUser> {
    return this.httpClient.put<IUser>(this.CHANGE_ROLE_URL + '/remove/' + username, this.getJsonForRoleResponseBody(role));
  }

  getJsonForRoleResponseBody(role: string) {
    const roleModel = {
      "name": role
    }
    return roleModel;
  }
}
