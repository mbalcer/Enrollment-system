import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {ISubjectGroup, SubjectGroup} from './subject-group.model';
import {SubjectGroupService} from './subject-group.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'fieldsOfStudy', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[];

  constructor(private subjectGroupService: SubjectGroupService, private router: Router) { }

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

  deleteGroup(row: ISubjectGroup) {
    this.subjectGroupService.deleteGroup(row.id).subscribe(result => {
      this.groups.splice(this.groups.indexOf(row), 1);
      this.refreshTable();
    }, error => console.log(error));
  }
}
