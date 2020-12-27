import {Component, OnInit, ViewChild} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';
import {NotificationsService} from 'angular2-notifications';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-my-groups',
  templateUrl: './my-groups.component.html',
  styleUrls: ['./my-groups.component.scss']
})
export class MyGroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'fieldsOfStudy', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[] = [];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private subjectGroupService: SubjectGroupService,
              private tokenStorage: TokenStorageService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getMyGroups();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
    this.dataSource.paginator = this.paginator;
  }

  getMyGroups() {
    const teacherUsername = this.tokenStorage.getUser().username;
    this.subjectGroupService.getAllGroupByTeacher(teacherUsername).subscribe(result => {
      this.groups = result;
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

}
