import {FieldOfStudy} from '../dashboard/components/university/field-of-study.model';

export interface IStudent {
  username: string;
  fullName: string;
  email: string;
  role: string;
  isActive: boolean;
  indexNumber: number;
  semester: number;
  fieldOfStudyDTO: FieldOfStudy;
}

export class Student implements IStudent {
  username: string;
  fullName: string;
  email: string;
  role: string;
  isActive: boolean;
  indexNumber: number;
  semester: number;
  fieldOfStudyDTO: FieldOfStudy;

  constructor() {
    this.username = '';
    this.fullName = '';
    this.email = '';
    this.role = '';
    this.isActive = false;
    this.indexNumber = 0;
    this.semester = 0;
    this.fieldOfStudyDTO = null;
  }

}
