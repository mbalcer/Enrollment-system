import {Component, OnInit} from '@angular/core';
import {IUser, User} from '../../../../user/model/user.model';
import {TokenStorageService} from '../../../../user/auth/token-storage.service';
import {UserService} from '../../../../user/service/user.service';
import {StudentService} from '../../../../user/service/student.service';
import {TeacherService} from '../../../../user/service/teacher.service';
import {IStudent} from '../../../../user/model/student.model';
import {ITeacher} from '../../../../user/model/teacher.model';
import {FieldOfStudyService} from '../../university/field-of-study.service';
import {IFieldOfStudy} from '../../university/field-of-study.model';
import {FacultyService} from '../../university/faculty.service';
import {IFaculty} from '../../university/faculty.model';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  user: IUser = new User();
  student: IStudent = null;
  teacher: ITeacher = null;

  fieldsOfStudy: IFieldOfStudy[] = [];
  faculties: IFaculty[] = [];

  constructor(private tokenStorageService: TokenStorageService,
              private userService: UserService,
              private studentService: StudentService,
              private teacherService: TeacherService,
              private fieldOfStudyService: FieldOfStudyService,
              private facultyService: FacultyService) {
  }

  ngOnInit(): void {
    this.getUser();
    this.getFieldsOfStudy();
    this.getFaculties();
  }

  public objectComparisonFunction(option, value): boolean {
    return option.id === value.id;
  }

  getFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe(result => {
      this.fieldsOfStudy = result;
    }, error => console.log(error));
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
    }, error => console.log(error));
  }

  getUser() {
    const user = this.tokenStorageService.getUser();
    const role = this.tokenStorageService.getActiveRole();
    if (role === 'STUDENT') {
      this.studentService.getStudentByUsername(user.username).subscribe(result => {
        this.user = result;
        this.student = result;
      }, err => console.log(err));
    } else if (role === 'TEACHER') {
      this.teacherService.getTeacherByUsername(user.username).subscribe(result => {
        this.user = result;
        this.teacher = result;
      }, err => console.log(err));
    } else {
      this.userService.getUser().subscribe(result => {
        this.user = result;
      }, err => console.log(err));
    }
  }

  editUser() {
    this.userService.putUser(this.user, this.user.username).subscribe(result => {
      this.user = result;
    }, error => console.log(error));
  }

  editStudent() {
    this.studentService.putStudent(this.student, this.student.username).subscribe(result => {
      this.student = result;
    }, error => console.log(error));
  }

  editTeacher() {
    this.teacherService.putTeacher(this.teacher, this.teacher.username).subscribe(result => {
      this.teacher = result;
    }, error => console.log(error));
  }
}
