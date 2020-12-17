import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {ISubjectGroup, SubjectGroup} from './subject-group.model';
import {SubjectGroupService} from './subject-group.service';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'fieldsOfStudy', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[] = [];

  constructor(private subjectGroupService: SubjectGroupService, private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getGroups();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
  }

  getGroups() {
    this.subjectGroupService.getAllGroups().subscribe(result => {
      this.groups = result;
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  deleteGroup(row: ISubjectGroup) {
    if (confirm('Are you sure you want to delete the group with id "' + row.id + '"?')) {
      this.subjectGroupService.deleteGroup(row.id).subscribe(result => {
        this.groups.splice(this.groups.indexOf(row), 1);
        this.refreshTable();
        this.notificationService.success("Delete group", "The group with id " + row.id + " was deleted.");
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    } else {
      this.notificationService.info("Delete group", "The group wasn't deleted");
    }
  }
}
