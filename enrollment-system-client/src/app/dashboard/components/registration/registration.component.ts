import {Component, OnInit, ViewChild} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {StudentService} from '../../../user/service/student.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';
import {Student} from '../../../user/model/student.model';
import {NotificationsService} from 'angular2-notifications';
import {FacultyService} from '../university/faculty.service';
import {DatePipe} from '@angular/common';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[] = [];
  student = new Student();
  blockedRegistration = false;
  messageBlockedRegistration = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private tokenStorage: TokenStorageService,
              private studentService: StudentService,
              private subjectGroupService: SubjectGroupService,
              private facultyService: FacultyService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    const user = this.tokenStorage.getUser();
    this.studentService.getStudentByUsername(user.username).subscribe(result => {
      this.student = result;
      this.isBlockedRegistration(this.student.fieldOfStudyDTO.abbreviationFaculty);
      this.getAllGroupToRegistration(result.fieldOfStudyDTO.id);
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
    this.dataSource.paginator = this.paginator;
    this.sortTable();
  }

  sortTable() {
    this.groups.sort((g1, g2) => {
      if (this.checkStudentInGroup(g1) === this.checkStudentInGroup(g2)) {
        return 0;
      } else if (this.checkStudentInGroup(g1)) {
        return -1;
      } else {
        return 1;
      }
    });
  }

  isBlockedRegistration(abbreviation: string) {
    this.facultyService.isBlocked(abbreviation).subscribe(result => {
      if (result) {
        this.blockedRegistration = true;
        this.messageBlockedRegistration = 'Registration is currently blocked. \nThe registration opening date: <b>' + new DatePipe("en-US").transform(result, 'yyyy-MM-dd HH:mm:ss') + '</b>';
      } else {
        this.blockedRegistration = false;
      }
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  getAllGroupToRegistration(fieldOfStudyId: number) {
    this.subjectGroupService.getAllRegistration(fieldOfStudyId).subscribe(result => {
      this.groups = result;
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  checkStudentInGroup(group: ISubjectGroup) {
    return group.studentsDTO.filter(student => this.student.username === student.username).length > 0;
  }

  addToGroup(row: ISubjectGroup) {
    this.groups.splice(this.groups.indexOf(row), 1);
    this.subjectGroupService.addStudentToGroup(row, this.student.username).subscribe(result => {
      this.groups.push(result);
      this.refreshTable();
      this.notificationService.success("Add to group", "You have been added to the group");
    }, err => this.notificationService.error(err.error.status, err.error.message));
  }

  removeFromGroup(row: ISubjectGroup) {
    this.groups.splice(this.groups.indexOf(row), 1);
    this.subjectGroupService.removeStudentFromGroup(row, this.student.username).subscribe(result => {
      this.groups.push(result);
      this.refreshTable();
      this.notificationService.success("Remove from group", "You have been removed from the group");
    }, err => this.notificationService.error(err.error.status, err.error.message));
  }

  getIndexOfStudentInGroup(group: ISubjectGroup) {
    return group.studentsDTO.findIndex(student => student.username === this.student.username);
  }
}
