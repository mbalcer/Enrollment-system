import {Component, OnInit} from '@angular/core';
import {SubjectGroup} from '../subject-group.model';
import {SubjectGroupService} from '../subject-group.service';
import {ActivatedRoute} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {Student} from '../../../../user/model/student.model';

@Component({
  selector: 'app-group-details',
  templateUrl: './group-details.component.html',
  styleUrls: ['./group-details.component.scss']
})
export class GroupDetailsComponent implements OnInit {
  displayedColumns: string[] = ['fullName', 'index', 'fieldOfStudy'];
  dataSourceStudents = new MatTableDataSource<Student>();

  group = new SubjectGroup();

  constructor(private subjectGroupService: SubjectGroupService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getGroup(this.route.snapshot.paramMap.get('id'));
  }

  refreshTableStudent() {
    this.dataSourceStudents = new MatTableDataSource<Student>(this.group.studentsDTO);
  }

  getGroup(id: string) {
    this.subjectGroupService.getGroup(Number(id)).subscribe(result => {
      this.group = result;
      this.refreshTableStudent();
    }, error => console.log(error));
  }
}
