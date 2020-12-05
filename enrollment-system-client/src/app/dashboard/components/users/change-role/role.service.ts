import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private ROLE_URL = environment.basePath + '/roles';

  constructor(private http: HttpClient) { }

  getAllRoles(): Observable<string[]> {
    return this.http.get<string[]>(this.ROLE_URL);
  }
}
