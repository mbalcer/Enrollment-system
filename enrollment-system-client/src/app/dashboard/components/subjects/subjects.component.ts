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
    if (this.checkUserIsAdmin()) {
      this.displayedColumns.push('actions');
    }
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

  deleteSubject(subject: ISubject) {
    if (confirm("Are you sure you want to delete the group with id " + subject.id + "?")) {
      this.subjectService.deleteSubject(subject.id).subscribe(result => {
        this.subjects.splice(this.subjects.indexOf(subject), 1);
        this.notificationService.success("Delete subject", "You deleted the subject with id " + subject.id);
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message))
    }
  }
}
