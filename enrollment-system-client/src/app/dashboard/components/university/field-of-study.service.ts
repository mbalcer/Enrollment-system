import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {IFieldOfStudy} from './field-of-study.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FieldOfStudyService {
  private FIELD_OF_STUDY_URL = environment.basePath + '/fieldOfStudy';

  constructor(private httpClient: HttpClient) {}

  getAllFieldsOfStudy(): Observable<IFieldOfStudy[]> {
    return this.httpClient.get<IFieldOfStudy[]>(this.FIELD_OF_STUDY_URL);
  }

  postFieldOfStudy(fieldOfStudy: IFieldOfStudy): Observable<IFieldOfStudy> {
    return this.httpClient.post<IFieldOfStudy>(this.FIELD_OF_STUDY_URL, fieldOfStudy);
  }

  putFieldOfStudy(fieldOfStudy: IFieldOfStudy): Observable<IFieldOfStudy> {
    return this.httpClient.put<IFieldOfStudy>(this.FIELD_OF_STUDY_URL + '/' + fieldOfStudy.id, fieldOfStudy);
  }

  deleteFieldOfStudy(id: number): Observable<any> {
    return this.httpClient.delete<IFieldOfStudy>(this.FIELD_OF_STUDY_URL + '/' + id);
  }
}
