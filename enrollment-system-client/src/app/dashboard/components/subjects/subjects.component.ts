import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {ISubject, Subject} from './subject.model';
import {SubjectService} from './subject.service';
import {MatPaginator} from '@angular/material/paginator';
import {NotificationsService} from 'angular2-notifications';
import {TokenStorageService} from '../../../user/auth/token-storage.service';

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

  constructor(private subjectService: SubjectService,
              private notificationService: NotificationsService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.getSubjects();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<Subject>(this.subjects);
    this.dataSource.paginator = this.paginator;
  }

  getSubjects() {
    this.subjectService.getAllSubjects().subscribe(result => {
      this.subjects = result;
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  checkUserIsAdmin() {
    return this.tokenStorage.isRole('ADMIN');
  }
}
