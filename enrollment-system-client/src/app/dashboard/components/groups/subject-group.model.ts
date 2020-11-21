import {IAppointment} from './appointment.model';
import {ISubject} from '../subjects/subject.model';
import {IFieldOfStudy} from '../university/field-of-study.model';
import {IStudent} from '../../../user/model/student.model';
import {Time} from '@angular/common';
import {GroupType} from '../../../model/enumeration/group-type.enum';

export interface ISubjectGroup {
  id: number;
  courseTime: Time;
  place: string;
  numberOfPlaces: number;
  type: GroupType;
  nameTeacher: string;
  timeTableDTO: IAppointment[];
  subjectDTO: ISubject;
  fieldsOfStudyDTO: IFieldOfStudy[];
  studentsDTO: IStudent[];
}

export class SubjectGroup implements ISubjectGroup {
  id: number;
  courseTime: Time;
  place: string;
  numberOfPlaces: number;
  type: GroupType;
  nameTeacher: string;
  timeTableDTO: IAppointment[];
  subjectDTO: ISubject;
  fieldsOfStudyDTO: IFieldOfStudy[];
  studentsDTO: IStudent[];

  constructor() {
    this.id = null;
    this.courseTime = null;
    this.place = '';
    this.numberOfPlaces = 0;
    this.type = GroupType.INACTIVE;
    this.nameTeacher = '';
    this.timeTableDTO = null;
    this.subjectDTO = null;
    this.fieldsOfStudyDTO = [];
    this.studentsDTO = null;
  }
}
