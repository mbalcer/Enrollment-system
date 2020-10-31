import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ISubject} from './subject.model';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {
  private SUBJECT_URL = environment.basePath + '/subject';

  constructor(private httpClient: HttpClient) { }

  getAllSubjects(): Observable<ISubject[]> {
    return this.httpClient.get<ISubject[]>(this.SUBJECT_URL);
  }

  getSubject(id: number): Observable<ISubject> {
    return this.httpClient.get<ISubject>(this.SUBJECT_URL + '/' + id);
  }

  postSubject(subject: ISubject): Observable<ISubject> {
    return this.httpClient.post<ISubject>(this.SUBJECT_URL, subject);
  }

  putSubject(subject: ISubject, id: number): Observable<ISubject> {
    return this.httpClient.put<ISubject>(this.SUBJECT_URL + '/' + id, subject);
  }

  deleteSubject(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.SUBJECT_URL + '/' + id);
  }
}
