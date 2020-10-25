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

  displayedColumnsFieldOfStudyTable: string[] = ['id', 'name', 'type', 'mode', 'abbreviationFaculty'];
  dataSourceFieldsOfStudy = new MatTableDataSource<FieldOfStudy>();
  fieldsOfStudy: IFieldOfStudy[];

  constructor(private facultyService: FacultyService, private fieldOfStudyService: FieldOfStudyService) { }

  ngOnInit(): void {
    this.getFaculties();
    this.getFieldsOfStudy();
  }

  refreshTable() {
    this.dataSourceFaculties = new MatTableDataSource<Faculty>(this.faculties);
    this.dataSourceFieldsOfStudy = new MatTableDataSource<FieldOfStudy>(this.fieldsOfStudy);
  }

  clearMessage() {
    this.facultyMessage = new FormMessage();
  }

  processError(error) {
    this.facultyMessage.type = TypeMessage.ERROR;
    if (error.error.message == null || error.error.message.length === 0) {
      this.facultyMessage.message = 'Server error. Try again later.';
    } else {
      this.facultyMessage.message = error.error.message;
    }
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
      this.refreshTable();
    }, error => this.processError(error));
  }

  deleteFaculty(row: IFaculty) {
    this.clearMessage();
    this.facultyService.deleteFaculty(row.id).subscribe(result => {
      this.faculties.splice(this.faculties.indexOf(row), 1);
      this.refreshTable();
      this.facultyMessage = {
        type: TypeMessage.SUCCESS,
        message: 'The faculty has been successfully deleted'
      };
    }, error => this.processError(error));
  }

  editFaculty(row: IFaculty) {
    this.clearMessage();
    this.facultyToSave = row;
  }

  saveFaculty() {
    this.clearMessage();
    if (this.facultyToSave.id != null && typeof this.facultyToSave.id === 'number') {
      this.facultyService.putFaculty(this.facultyToSave).subscribe(result => {
        this.facultyToSave = new Faculty();
        this.facultyMessage = {
          type: TypeMessage.SUCCESS,
          message: 'The faculty has been successfully updated'
        };
      }, error => this.processError(error));
    } else {
      this.facultyService.postFaculty(this.facultyToSave).subscribe(result => {
        this.faculties.push(result);
        this.facultyToSave = new Faculty();
        this.refreshTable();
        this.facultyMessage = {
          type: TypeMessage.SUCCESS,
          message: 'The faculty has been successfully added'
        };
      }, error => this.processError(error));
    }
  }

  getFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe(result => {
      this.fieldsOfStudy = result;
      this.refreshTable();
    }, error => console.log(error));
  }
}
