import {IFaculty} from '../../dashboard/components/university/faculty.model';

export interface ITeacher {
  username: string;
  fullName: string;
  email: string;
  role: string;
  isActive: boolean;
  room: string;
  consultations: string;
  facultyDTO: IFaculty;
}

export class Teacher implements ITeacher {
  username: string;
  fullName: string;
  email: string;
  role: string;
  isActive: boolean;
  room: string;
  consultations: string;
  facultyDTO: IFaculty;

  constructor() {
    this.username = '';
    this.fullName = '';
    this.email = '';
    this.role = '';
    this.isActive = false;
    this.room = '';
    this.consultations = '';
    this.facultyDTO = null;
  }
}
