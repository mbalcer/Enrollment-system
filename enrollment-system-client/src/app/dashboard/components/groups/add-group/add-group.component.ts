import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SubjectGroupService} from '../subject-group.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SubjectGroup} from '../subject-group.model';
import {ISubject} from '../../subjects/subject.model';
import {ITeacher} from '../../../../user/model/teacher.model';
import {SubjectService} from '../../subjects/subject.service';
import {TeacherService} from '../../../../user/service/teacher.service';
import {FormControl, NgForm} from '@angular/forms';
import {Observable} from 'rxjs';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {map, startWith} from 'rxjs/operators';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {IFieldOfStudy} from '../../university/field-of-study.model';
import {FieldOfStudyService} from '../../university/field-of-study.service';
import {TokenStorageService} from '../../../../user/auth/token-storage.service';
import {IAppointment} from '../appointment.model';
import {DatePipe} from '@angular/common';
import {MatListOption} from '@angular/material/list';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.component.html',
  styleUrls: ['./add-group.component.scss']
})
export class AddGroupComponent implements OnInit {
  teacherUsername: string = null;
  groupToAdd = new SubjectGroup();
  isAdd = true;
  subjects: ISubject[] = [];
  teachers: ITeacher[] = [];
  allFieldsOfStudy: IFieldOfStudy[] = [];
  timeTable: IAppointment[] = [];

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = false;
  separatorKeysCodes = [ENTER, COMMA];

  fieldOfStudyCtrl = new FormControl();
  filteredFieldsOfStudy: Observable<any[]>;

  @ViewChild('subjectInput') subjectInput: ElementRef;

  constructor(private subjectGroupService: SubjectGroupService,
              private subjectService: SubjectService,
              private teacherService: TeacherService,
              private fieldOfStudyService: FieldOfStudyService,
              private route: ActivatedRoute,
              private router: Router,
              private tokenStorage: TokenStorageService) {
    this.filteredFieldsOfStudy = this.fieldOfStudyCtrl.valueChanges.pipe(
      startWith(null),
      map((fieldOfStudy: string | null) =>
        fieldOfStudy ? this.filter(fieldOfStudy) : this.allFieldsOfStudy
      )
    );
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
      this.isAdd = false;
      this.getGroup(id);
    }

    const teacher = this.tokenStorage.getUser();
    if (teacher.role === 'TEACHER') {
      this.teacherUsername = teacher.username;
    }
    this.getSubjects();
    this.getTeachers();
    this.getFieldsOfStudy();
  }

  public objectComparisonFunction(option, value): boolean {
    return option.id === value.id;
  }

  getGroup(id) {
    this.subjectGroupService.getGroup(Number(id)).subscribe(result => {
      this.groupToAdd = result;
      this.timeTable = this.groupToAdd.timeTableDTO;
    }, error => console.log(error));
  }

  getSubjects() {
    this.subjectService.getAllSubjects().subscribe(result => {
      this.subjects = result;
    }, error => console.log(error));
  }

  getTeachers() {
    this.teacherService.getAllTeachers().subscribe(result => {
      this.teachers = result;
      if (this.teacherUsername !== null) {
        this.teachers = this.teachers.filter(teacher => teacher.username === this.teacherUsername);
        this.groupToAdd.nameTeacher = this.teachers[0].fullName;
      }
    }, error => console.log(error));
  }

  getFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe(result => {
      this.allFieldsOfStudy = result;
      if (this.groupToAdd.fieldsOfStudyDTO.length > 0) {
        this.groupToAdd.fieldsOfStudyDTO.forEach(fieldOfStudy => {
          const index = this.allFieldsOfStudy.findIndex(f => f.id === fieldOfStudy.id);
          this.allFieldsOfStudy.splice(index, 1);
        });
      }
    }, error => console.log(error));
  }

  saveGroup() {
    this.groupToAdd.timeTableDTO = this.timeTable;
    if (this.isAdd) {
      this.subjectGroupService.postGroup(this.groupToAdd).subscribe(result => {
        // success message
        this.groupToAdd = new SubjectGroup();
      }, error => console.log(error));
    } else {
      this.subjectGroupService.putGroup(this.groupToAdd, this.groupToAdd.id).subscribe(result => {
        // success message
        this.groupToAdd = new SubjectGroup();
        this.isAdd = true;
        this.router.navigateByUrl('/dashboard/groups/add');
      }, error => console.log(error));
    }
  }

  addAppointment(timeTableForm: NgForm) {
    const startDate = new DatePipe('en-US').transform(timeTableForm.form.value.startDate, 'yyyy-MM-ddTHH:mm:ss');
    const endDate = new DatePipe('en-US').transform(timeTableForm.form.value.endDate, 'yyyy-MM-ddTHH:mm:ss');

    const appointment: IAppointment = {
      startTime: startDate,
      endTime: endDate
    };

    this.timeTable.push(appointment);
    timeTableForm.resetForm();
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      const getFieldOfStudy = this.allFieldsOfStudy.filter(
        fieldOfStudy => fieldOfStudy.name.toLowerCase() === value.trim().toLowerCase())[0];
      if (getFieldOfStudy !== undefined) {
        this.groupToAdd.fieldsOfStudyDTO.push(getFieldOfStudy);
        this.allFieldsOfStudy.splice(this.allFieldsOfStudy.indexOf(getFieldOfStudy), 1);
      }
    }

    if (input) {
      input.value = '';
    }

    this.fieldOfStudyCtrl.setValue(null);
  }

  remove(fieldOfStudy: IFieldOfStudy): void {
    const index = this.groupToAdd.fieldsOfStudyDTO.indexOf(fieldOfStudy);

    if (index >= 0) {
      this.groupToAdd.fieldsOfStudyDTO.splice(index, 1);
    }
    this.allFieldsOfStudy.push(fieldOfStudy);
  }

  filter(value: string): IFieldOfStudy[] {
    if (value && value.length !== 0) {
      const filterValue = value.toString().toLocaleLowerCase();
      return this.allFieldsOfStudy.filter(fieldOfStudy => fieldOfStudy.name.toLocaleLowerCase().indexOf(filterValue) === 0);
    } else {
      return this.allFieldsOfStudy;
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const value = this.allFieldsOfStudy.filter(fieldOfStudy => fieldOfStudy.name === event.option.value.name)[0];
    this.groupToAdd.fieldsOfStudyDTO.push(value);
    this.subjectInput.nativeElement.value = '';
    this.fieldOfStudyCtrl.setValue(null);
    this.allFieldsOfStudy.splice(this.allFieldsOfStudy.indexOf(value), 1);
  }

  deleteAppointments(selected: MatListOption[]) {
    selected.forEach(option => {
      const appointment: IAppointment = option.value;
      this.timeTable.splice(this.timeTable.indexOf(appointment), 1);
    });
  }
}
