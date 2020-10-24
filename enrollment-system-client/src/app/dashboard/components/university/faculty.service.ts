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

  constructor(private httpClient: HttpClient) { }

  getAllFaculties(): Observable<IFaculty[]> {
    return this.httpClient.get<IFaculty[]>(this.GET_ALL_FACULTY_URL);
  }

  saveFaculty(faculty: IFaculty): Observable<IFaculty> {
    return this.httpClient.post<IFaculty>(this.POST_FACULTY_URL, faculty);
  }
}
