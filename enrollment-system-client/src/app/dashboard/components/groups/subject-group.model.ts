import {IAppointment} from './appointment.model';
import {ISubject} from '../subjects/subject.model';
import {IFieldOfStudy} from '../university/field-of-study.model';
import {IStudent} from '../../../user/model/student.model';
import {Time} from '@angular/common';

export interface ISubjectGroup {
  id: number;
  courseTime: Time;
  place: string;
  numberOfPlaces: number;
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
    this.nameTeacher = '';
    this.timeTableDTO = null;
    this.subjectDTO = null;
    this.fieldsOfStudyDTO = null;
    this.studentsDTO = null;
  }
}
