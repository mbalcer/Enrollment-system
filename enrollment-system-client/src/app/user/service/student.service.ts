import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IStudent, Student} from '../model/student.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private STUDENT_URL = environment.basePath + '/student';

  constructor(private httpClient: HttpClient) {
  }

  getStudentByUsername(username: string): Observable<Student> {
    return this.httpClient.get<Student>(this.STUDENT_URL + '/byUsername/' + username);
  }

  putStudent(student: IStudent, username: string): Observable<Student> {
    return this.httpClient.put<Student>(this.STUDENT_URL + '/' + username, student);
  }
}
