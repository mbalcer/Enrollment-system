import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../user/service/user.service';
import {IUser, User} from '../../../user/model/user.model';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {StudentService} from '../../../user/service/student.service';
import {TeacherService} from '../../../user/service/teacher.service';
import {IStudent} from '../../../user/model/student.model';
import {ITeacher} from '../../../user/model/teacher.model';

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
              private teacherService: TeacherService) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {
    const user = this.tokenStorageService.getUser();
    const firstRole = user.role[0];
    if (firstRole === 'STUDENT') {
      this.studentService.getStudentByUsername(user.username).subscribe(result => {
        this.user = result;
        this.student = result;
      }, err => console.log(err));
    } else if (firstRole === 'TEACHER') {
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

}
