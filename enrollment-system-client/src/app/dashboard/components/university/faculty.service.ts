import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IFaculty} from './faculty.model';

@Injectable({
  providedIn: 'root'
})
export class FacultyService {
  private FACULTY_URL = environment.basePath + '/faculty';
  private GET_ALL_FACULTY_URL = this.FACULTY_URL;
  private POST_FACULTY_URL = this.FACULTY_URL;
  private PUT_FACULTY_URL = this.FACULTY_URL;
  private DELETE_FACULTY_URL = this.FACULTY_URL;

  constructor(private httpClient: HttpClient) { }

  getAllFaculties(): Observable<IFaculty[]> {
    return this.httpClient.get<IFaculty[]>(this.GET_ALL_FACULTY_URL);
  }

  postFaculty(faculty: IFaculty): Observable<IFaculty> {
    return this.httpClient.post<IFaculty>(this.POST_FACULTY_URL, faculty);
  }

  putFaculty(faculty: IFaculty): Observable<IFaculty> {
    return this.httpClient.put<IFaculty>(this.PUT_FACULTY_URL + '/' + faculty.id, faculty);
  }

  deleteFaculty(id: number): Observable<any> {
    return this.httpClient.delete(this.DELETE_FACULTY_URL + '/' + id);
  }
}
