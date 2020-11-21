import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';
import {GroupType} from '../../../model/enumeration/group-type.enum';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'fieldsOfStudy', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  requests: ISubjectGroup[];

  constructor(private subjectGroupService: SubjectGroupService) { }

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
    }, error => console.log(error));
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
    }, error => console.log(error));
  }

  getListFieldsOfStudy(group: SubjectGroup) {
    let list = '';
    group.fieldsOfStudyDTO.forEach(fieldOfStudy => {
      list += fieldOfStudy.name + ', ';
    });
    list = list.substr(0, list.length - 2);
    return list;
  }
}
