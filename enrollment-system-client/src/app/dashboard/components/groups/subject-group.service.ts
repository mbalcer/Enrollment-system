import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ISubjectGroup} from './subject-group.model';
import {GroupType} from '../../../model/enumeration/group-type.enum';

@Injectable({
  providedIn: 'root'
})
export class SubjectGroupService {
  private SUBJECT_GROUP_URL = environment.basePath + '/subjectGroup';

  constructor(private httpClient: HttpClient) { }

  getAllGroups(): Observable<ISubjectGroup[]> {
    return this.httpClient.get<ISubjectGroup[]>(this.SUBJECT_GROUP_URL);
  }

  getAllGroupByTeacher(teacher: string): Observable<ISubjectGroup[]> {
    return this.httpClient.get<ISubjectGroup[]>(this.SUBJECT_GROUP_URL + '/byTeacher/' + teacher);
  }

  getGroup(id: number): Observable<ISubjectGroup> {
    return this.httpClient.get<ISubjectGroup>(this.SUBJECT_GROUP_URL + '/' + id);
  }

  getAllRequests(): Observable<ISubjectGroup[]> {
    return this.httpClient.get<ISubjectGroup[]>(this.SUBJECT_GROUP_URL + '/requests');
  }

  getAllRegistration(fieldOfStudyId: number): Observable<ISubjectGroup[]> {
    return this.httpClient.get<ISubjectGroup[]>(this.SUBJECT_GROUP_URL + '/registration/' + fieldOfStudyId);
  }

  postGroup(group: ISubjectGroup): Observable<ISubjectGroup> {
    return this.httpClient.post<ISubjectGroup>(this.SUBJECT_GROUP_URL, group);
  }

  putGroup(group: ISubjectGroup, id: number): Observable<ISubjectGroup> {
    return this.httpClient.put<ISubjectGroup>(this.SUBJECT_GROUP_URL + '/' + id, group);
  }

  addStudentToGroup(group: ISubjectGroup, usernameStudent: string): Observable<ISubjectGroup> {
    return this.httpClient.put<ISubjectGroup>(this.SUBJECT_GROUP_URL + '/add/student/' + usernameStudent, group);
  }

  removeStudentFromGroup(group: ISubjectGroup, usernameStudent: string): Observable<ISubjectGroup> {
    return this.httpClient.put<ISubjectGroup>(this.SUBJECT_GROUP_URL + "/remove/student/" + usernameStudent, group);
  }

  deleteGroup(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.SUBJECT_GROUP_URL + '/' + id);
  }

  updateTypeGroup(type: GroupType, id: number): Observable<ISubjectGroup> {
    return this.httpClient.put<ISubjectGroup>(this.SUBJECT_GROUP_URL + '/type/' + id, type);
  }
}
