import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../user/service/user.service';
import {IUser, User} from '../../../user/model/user.model';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {StudentService} from '../../../user/service/student.service';
import {TeacherService} from '../../../user/service/teacher.service';
import {IStudent} from '../../../user/model/student.model';
import {ITeacher} from '../../../user/model/teacher.model';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: IUser = new User();
  student: IStudent = null;
  teacher: ITeacher = null;

  constructor(private tokenStorageService: TokenStorageService,
              private userService: UserService,
              private studentService: StudentService,
              private teacherService: TeacherService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getUser();
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

}
