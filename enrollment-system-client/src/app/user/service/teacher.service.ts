import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ITeacher} from '../model/teacher.model';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  private TEACHER_URL = environment.basePath + '/teacher';

  constructor(private httpClient: HttpClient) { }

  getAllTeachers(): Observable<ITeacher[]> {
    return this.httpClient.get<ITeacher[]>(this.TEACHER_URL);
  }
}
