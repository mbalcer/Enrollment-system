import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {ISubjectGroup, SubjectGroup} from './subject-group.model';
import {SubjectGroupService} from './subject-group.service';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'fieldsOfStudy', 'numberOfEnrolled', 'numberOfPlaces'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[];

  constructor(private subjectGroupService: SubjectGroupService) { }

  ngOnInit(): void {
    this.getGroups();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
  }

  getGroups() {
    this.subjectGroupService.getAllGroups().subscribe(result => {
      console.log(result);
      this.groups = result;
      console.log(this.groups);
      this.refreshTable();
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
