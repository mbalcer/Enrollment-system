import {IFaculty} from '../../dashboard/components/university/faculty.model';

export interface ITeacher {
  username: string;
  fullName: string;
  email: string;
  role: string;
  isActive: boolean;
  facultyDTO: IFaculty;
}

export class Teacher implements ITeacher {
  username: string;
  fullName: string;
  email: string;
  role: string;
  isActive: boolean;
  facultyDTO: IFaculty;

  constructor() {
    this.username = '';
    this.fullName = '';
    this.email = '';
    this.role = '';
    this.isActive = false;
    this.facultyDTO = null;
  }
}
