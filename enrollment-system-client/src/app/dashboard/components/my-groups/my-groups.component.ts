import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-my-groups',
  templateUrl: './my-groups.component.html',
  styleUrls: ['./my-groups.component.scss']
})
export class MyGroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'fieldsOfStudy', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[] = [];

  constructor(private subjectGroupService: SubjectGroupService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.getMyGroups();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
  }

  getMyGroups() {
    const teacherUsername = this.tokenStorage.getUser().username;
    this.subjectGroupService.getAllGroupByTeacher(teacherUsername).subscribe(result => {
      this.groups = result;
      this.refreshTable();
    }, error => console.log(error));
  }

}
