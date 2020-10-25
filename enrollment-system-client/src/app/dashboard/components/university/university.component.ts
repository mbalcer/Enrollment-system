import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Faculty, IFaculty} from './faculty.model';
import {FacultyService} from './faculty.service';
import {FormMessage} from '../../../model/form-message.model';
import {TypeMessage} from '../../../model/enumeration/type-message.enum';
import {FieldOfStudy, IFieldOfStudy} from './field-of-study.model';
import {FieldOfStudyService} from './field-of-study.service';

@Component({
  selector: 'app-university',
  templateUrl: './university.component.html',
  styleUrls: ['./university.component.scss']
})
export class UniversityComponent implements OnInit {
  public TypeMessage = TypeMessage;

  displayedColumnsFacultyTable: string[] = ['id', 'name', 'address', 'abbreviation', 'actions'];
  dataSourceFaculties = new MatTableDataSource<Faculty>();
  faculties: IFaculty[];
  facultyToSave = new Faculty();
  facultyMessage = new FormMessage();

  displayedColumnsFieldOfStudyTable: string[] = ['id', 'name', 'type', 'mode', 'abbreviationFaculty', 'actions'];
  dataSourceFieldsOfStudy = new MatTableDataSource<FieldOfStudy>();
  fieldsOfStudy: IFieldOfStudy[];
  fieldOfStudyToSave = new FieldOfStudy();
  fieldOfStudyMessage = new FormMessage();

  constructor(private facultyService: FacultyService, private fieldOfStudyService: FieldOfStudyService) { }

  ngOnInit(): void {
    this.getFaculties();
    this.getFieldsOfStudy();
  }

  refreshTables() {
    this.dataSourceFaculties = new MatTableDataSource<Faculty>(this.faculties);
    this.dataSourceFieldsOfStudy = new MatTableDataSource<FieldOfStudy>(this.fieldsOfStudy);
  }

  clearMessages() {
    this.facultyMessage = new FormMessage();
    this.fieldOfStudyMessage = new FormMessage();
  }

  processError(message: FormMessage, error) {
    message.type = TypeMessage.ERROR;
    if (error.error.message == null || error.error.message.length === 0) {
      message.message = 'Server error. Try again later.';
    } else {
      message.message = error.error.message;
    }
  }

  processSuccess(message: FormMessage, text) {
    message.type = TypeMessage.SUCCESS;
    message.message = text;
  }

  createSuccessMessage(object: string, operation: string) {
    return 'The ' + object + ' has been successfully ' + operation;
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
      this.refreshTables();
    }, error => this.processError(this.facultyMessage, error));
  }

  deleteFaculty(row: IFaculty) {
    this.clearMessages();
    this.facultyService.deleteFaculty(row.id).subscribe(result => {
      this.faculties.splice(this.faculties.indexOf(row), 1);
      this.refreshTables();
      this.processSuccess(this.facultyMessage, this.createSuccessMessage('faculty', 'deleted'));
    }, error => this.processError(this.facultyMessage, error));
  }

  editFaculty(row: IFaculty) {
    this.clearMessages();
    this.facultyToSave = row;
  }

  saveFaculty() {
    this.clearMessages();
    if (this.facultyToSave.id != null && typeof this.facultyToSave.id === 'number') {
      this.facultyService.putFaculty(this.facultyToSave).subscribe(result => {
        this.facultyToSave = new Faculty();
        this.processSuccess(this.facultyMessage, this.createSuccessMessage('faculty', 'updated'));
      }, error => this.processError(this.facultyMessage, error));
    } else {
      this.facultyService.postFaculty(this.facultyToSave).subscribe(result => {
        this.faculties.push(result);
        this.facultyToSave = new Faculty();
        this.refreshTables();
        this.processSuccess(this.facultyMessage, this.createSuccessMessage('faculty', 'added'));
      }, error => this.processError(this.facultyMessage, error));
    }
  }

  getFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe(result => {
      this.fieldsOfStudy = result;
      this.refreshTables();
    }, error => this.processError(this.fieldOfStudyMessage, error));
  }

  editFieldOfStudy(row: IFieldOfStudy) {
    this.clearMessages();
    this.fieldOfStudyToSave = row;
  }

  deleteFieldOfStudy(row: IFieldOfStudy) {
    this.clearMessages();
    this.fieldOfStudyService.deleteFieldOfStudy(row.id).subscribe(result => {
      this.fieldsOfStudy.splice(this.fieldsOfStudy.indexOf(row), 1);
      this.refreshTables();
      this.processSuccess(this.fieldOfStudyMessage, this.createSuccessMessage('field of study', 'deleted'));
    }, error => this.processError(this.fieldOfStudyMessage, error));
  }

  saveFieldOfStudy() {
    this.clearMessages();
    if (this.fieldOfStudyToSave.id != null && typeof this.fieldOfStudyToSave.id === 'number') {
      this.fieldOfStudyService.putFieldOfStudy(this.fieldOfStudyToSave).subscribe(result => {
        this.fieldOfStudyToSave = new FieldOfStudy();
        this.processSuccess(this.fieldOfStudyMessage, this.createSuccessMessage('field of study', 'updated'));
      }, error => this.processError(this.fieldOfStudyMessage, error));
    } else {
      this.fieldOfStudyService.postFieldOfStudy(this.fieldOfStudyToSave).subscribe(result => {
        this.fieldsOfStudy.push(result);
        this.fieldOfStudyToSave = new FieldOfStudy();
        this.refreshTables();
        this.processSuccess(this.fieldOfStudyMessage, this.createSuccessMessage('field of study', 'added'));
      }, error => this.processError(this.fieldOfStudyMessage, error));
    }
  }
}
