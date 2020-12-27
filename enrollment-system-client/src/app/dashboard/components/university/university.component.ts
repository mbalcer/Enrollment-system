import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Faculty, IFaculty} from './faculty.model';
import {FacultyService} from './faculty.service';
import {FieldOfStudy, IFieldOfStudy} from './field-of-study.model';
import {FieldOfStudyService} from './field-of-study.service';
import {NotificationsService} from 'angular2-notifications';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-university',
  templateUrl: './university.component.html',
  styleUrls: ['./university.component.scss']
})
export class UniversityComponent implements OnInit {
  displayedColumnsFacultyTable: string[] = ['id', 'name', 'address', 'abbreviation', 'actions'];
  dataSourceFaculties = new MatTableDataSource<Faculty>();
  faculties: IFaculty[];
  facultyToSave = new Faculty();

  displayedColumnsFieldOfStudyTable: string[] = ['id', 'name', 'type', 'mode', 'abbreviationFaculty', 'actions'];
  dataSourceFieldsOfStudy = new MatTableDataSource<FieldOfStudy>();
  fieldsOfStudy: IFieldOfStudy[];
  fieldOfStudyToSave = new FieldOfStudy();

  studyTypes = ['FIRST_CYCLE', 'SECOND_CYCLE', 'THIRD_CYCLE', 'LONG_CYCLE'];
  studyModes = ['FULL_TIME', 'PART_TIME'];

  @ViewChild(MatPaginator) paginatorFieldOfStudy: MatPaginator;

  constructor(private facultyService: FacultyService,
              private fieldOfStudyService: FieldOfStudyService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getFaculties();
    this.getFieldsOfStudy();
  }

  refreshTables() {
    this.dataSourceFaculties = new MatTableDataSource<Faculty>(this.faculties);
    this.dataSourceFieldsOfStudy = new MatTableDataSource<FieldOfStudy>(this.fieldsOfStudy);
    this.dataSourceFieldsOfStudy.paginator = this.paginatorFieldOfStudy;
  }

  processError(err) {
    let message;
    if (err.error.message == null || err.error.message.length === 0) {
      message = 'Server error. Try again later.';
    } else {
      message = err.error.message;
    }

    this.notificationService.error(err.status + ': ' + err.error.status, message);
  }

  processSuccess(title, text) {
    this.notificationService.success(title, text);
  }

  createSuccessMessage(object: string, operation: string) {
    return 'The ' + object + ' has been successfully ' + operation;
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
      this.refreshTables();
    }, error => this.processError(error));
  }

  deleteFaculty(row: IFaculty) {
    if (confirm("Are you sure you want to delete the faculty with the name'" + row.name + "'?")) {
      this.facultyService.deleteFaculty(row.id).subscribe(result => {
        this.faculties.splice(this.faculties.indexOf(row), 1);
        this.refreshTables();
        this.processSuccess('Delete faculty', this.createSuccessMessage('faculty', 'deleted'));
      }, error => this.processError(error));
    } else {
      this.notificationService.info("Delete Faculty", "The Faculty wasn't deleted");
    }
  }

  editFaculty(row: IFaculty) {
    this.facultyToSave = row;
  }

  saveFaculty(form) {
    if (this.facultyToSave.id != null && typeof this.facultyToSave.id === 'number') {
      this.facultyService.putFaculty(this.facultyToSave).subscribe(result => {
        this.facultyToSave = new Faculty();
        this.refreshTables();
        this.processSuccess('Update faculty', this.createSuccessMessage('faculty', 'updated'));
        form.resetForm();
      }, error => this.processError(error));
    } else {
      this.facultyService.postFaculty(this.facultyToSave).subscribe(result => {
        this.faculties.push(result);
        this.facultyToSave = new Faculty();
        this.refreshTables();
        this.processSuccess('Create faculty', this.createSuccessMessage('faculty', 'added'));
        form.resetForm();
      }, error => this.processError(error));
    }
  }

  getFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe(result => {
      this.fieldsOfStudy = result;
      this.refreshTables();
    }, error => this.processError(error));
  }

  editFieldOfStudy(row: IFieldOfStudy) {
    this.fieldOfStudyToSave = row;
  }

  deleteFieldOfStudy(row: IFieldOfStudy) {
    if (confirm("Are you sure you want to delete the field of study with the name'" + row.name + "'?")) {
      this.fieldOfStudyService.deleteFieldOfStudy(row.id).subscribe(result => {
        this.fieldsOfStudy.splice(this.fieldsOfStudy.indexOf(row), 1);
        this.refreshTables();
        this.processSuccess('Delete Field of Study', this.createSuccessMessage('field of study', 'deleted'));
      }, error => this.processError(error));
    } else {
      this.notificationService.info("Delete Field of Study", "The Field of Study wasn't deleted");
    }
  }

  saveFieldOfStudy(form) {
    if (this.fieldOfStudyToSave.id != null && typeof this.fieldOfStudyToSave.id === 'number') {
      this.fieldOfStudyService.putFieldOfStudy(this.fieldOfStudyToSave).subscribe(result => {
        this.fieldOfStudyToSave = new FieldOfStudy();
        form.resetForm();
        this.processSuccess('Update Field of Study', this.createSuccessMessage('field of study', 'updated'));
      }, error => this.processError(error));
    } else {
      this.fieldOfStudyService.postFieldOfStudy(this.fieldOfStudyToSave).subscribe(result => {
        this.fieldsOfStudy.push(result);
        this.fieldOfStudyToSave = new FieldOfStudy();
        this.refreshTables();
        form.resetForm();
        this.processSuccess('Create Field of Study', this.createSuccessMessage('field of study', 'added'));
      }, error => this.processError(error));
    }
  }
}
