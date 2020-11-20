import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ITeacher, Teacher} from '../model/teacher.model';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  private TEACHER_URL = environment.basePath + '/teacher';

  constructor(private httpClient: HttpClient) { }

  getAllTeachers(): Observable<ITeacher[]> {
    return this.httpClient.get<ITeacher[]>(this.TEACHER_URL);
  }

  getTeacherByUsername(username: string): Observable<Teacher> {
    return this.httpClient.get<Teacher>(this.TEACHER_URL + '/byUsername/' + username);
  }
}
