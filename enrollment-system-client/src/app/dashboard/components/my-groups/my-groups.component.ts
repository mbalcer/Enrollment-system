import {Component, OnInit, ViewChild} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';
import {NotificationsService} from 'angular2-notifications';
import {MatPaginator} from '@angular/material/paginator';
import {PdfService} from './pdf.service';

@Component({
  selector: 'app-my-groups',
  templateUrl: './my-groups.component.html',
  styleUrls: ['./my-groups.component.scss']
})
export class MyGroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'fieldsOfStudy', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[] = [];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private subjectGroupService: SubjectGroupService,
              private tokenStorage: TokenStorageService,
              private notificationService: NotificationsService,
              private pdfService: PdfService) { }

  ngOnInit(): void {
    this.getMyGroups();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
    this.dataSource.paginator = this.paginator;
  }

  getMyGroups() {
    const teacherUsername = this.tokenStorage.getUser().username;
    this.subjectGroupService.getAllGroupByTeacher(teacherUsername).subscribe(result => {
      this.groups = result;
      this.refreshTable();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  exportPdf(row: ISubjectGroup) {
    this.pdfService.getPdfStream(row.id).subscribe(
      (data: Blob) => {
      const file = new Blob([data], { type: 'application/pdf' })
      const fileURL = URL.createObjectURL(file);

      let a         = document.createElement('a');
      a.href        = fileURL;
      a.target      = '_blank';
      a.download    = 'group' + row.id + '.pdf';
      document.body.appendChild(a);
      a.click();
    });
  }
}
