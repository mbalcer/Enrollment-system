import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';
import {GroupType} from '../../../model/enumeration/group-type.enum';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'fieldsOfStudy', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  requests: ISubjectGroup[] = [];

  constructor(private subjectGroupService: SubjectGroupService, private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getRequests();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.requests);
  }

  getRequests() {
    this.subjectGroupService.getAllRequests().subscribe(result => {
      this.requests = result;
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  acceptGroup(row: ISubjectGroup) {
    this.updateTypeGroup(GroupType.ACCEPTED, row);
  }

  dontAcceptGroup(row: ISubjectGroup) {
    this.updateTypeGroup(GroupType.NON_ACCEPTED, row);
  }

  updateTypeGroup(type: GroupType, group: ISubjectGroup) {
    this.subjectGroupService.updateTypeGroup(type, group.id).subscribe(result => {
      this.requests.splice(this.requests.indexOf(group), 1);
      if (type == GroupType.ACCEPTED) {
        this.notificationService.success("Request", "You have accepted the new group");
      } else {
        this.notificationService.success("Request", "You have don't accepted the new group");
      }
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }
}
