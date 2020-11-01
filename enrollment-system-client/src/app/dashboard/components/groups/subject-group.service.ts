import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ISubjectGroup} from './subject-group.model';

@Injectable({
  providedIn: 'root'
})
export class SubjectGroupService {
  private SUBJECT_GROUP_URL = environment.basePath + '/subjectGroup';

  constructor(private httpClient: HttpClient) { }

  getAllGroups(): Observable<ISubjectGroup[]> {
    return this.httpClient.get<ISubjectGroup[]>(this.SUBJECT_GROUP_URL);
  }
}
