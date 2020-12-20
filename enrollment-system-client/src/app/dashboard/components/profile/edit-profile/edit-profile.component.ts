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
import {NotificationsService} from 'angular2-notifications';

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
              private facultyService: FacultyService,
              private notificationService: NotificationsService) {
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
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  getUser() {
    const user = this.tokenStorageService.getUser();
    const role = this.tokenStorageService.getActiveRole();
    if (role === 'STUDENT') {
      this.studentService.getStudentByUsername(user.username).subscribe(result => {
        this.user = result;
        this.student = result;
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    } else if (role === 'TEACHER') {
      this.teacherService.getTeacherByUsername(user.username).subscribe(result => {
        this.user = result;
        this.teacher = result;
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    } else {
      this.userService.getUser().subscribe(result => {
        this.user = result;
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    }
  }

  editUser() {
    this.userService.putUser(this.user, this.user.username).subscribe(result => {
      this.user = result;
      this.notificationService.success("Edit user", "You have updated your profile");
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  editStudent() {
    this.studentService.putStudent(this.student, this.student.username).subscribe(result => {
      this.student = result;
      this.notificationService.success("Edit student", "You have updated your profile");
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  editTeacher() {
    this.teacherService.putTeacher(this.teacher, this.teacher.username).subscribe(result => {
      this.teacher = result;
      this.notificationService.success("Edit teacher", "You have updated your profile");
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }
}
