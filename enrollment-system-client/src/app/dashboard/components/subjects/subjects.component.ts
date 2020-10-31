import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {ISubject, Subject} from './subject.model';
import {SubjectService} from './subject.service';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss']
})
export class SubjectsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'courseType', 'ECTS'];
  dataSource = new MatTableDataSource<Subject>();
  subjects: ISubject[];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private subjectService: SubjectService) { }

  ngOnInit(): void {
    this.getSubjects();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<Subject>(this.subjects);
    this.dataSource.paginator = this.paginator;
  }

  getSubjects() {
    this.subjectService.getAllSubjects().subscribe(result => {
      console.log(result);
      this.subjects = result;
      console.log(this.subjects);
      this.refreshTable();
    }, error => console.log(error));
  }

}
