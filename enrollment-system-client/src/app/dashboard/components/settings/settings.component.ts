import {Component, OnInit} from '@angular/core';
import {FacultyService} from '../university/faculty.service';
import {NotificationsService} from 'angular2-notifications';
import {IFaculty} from '../university/faculty.model';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  faculties: IFaculty[] = [];

  constructor(private facultyService: FacultyService, private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getFaculties();
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  saveRegistrationDate(faculty: IFaculty) {
    faculty.startRegistration = new DatePipe('en-US').transform(faculty.startRegistration, 'yyyy-MM-ddTHH:mm:ss');
    this.facultyService.putFaculty(faculty).subscribe(result => {
      this.faculties[this.faculties.indexOf(faculty)] = result;
      this.notificationService.success("You set start registration date for " + result.name + " on " + result.startRegistration);
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message))
  }
}
