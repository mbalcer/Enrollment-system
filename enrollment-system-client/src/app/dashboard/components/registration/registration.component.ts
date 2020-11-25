import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {StudentService} from '../../../user/service/student.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {ISubjectGroup, SubjectGroup} from '../groups/subject-group.model';
import {MatTableDataSource} from '@angular/material/table';
import {Student} from '../../../user/model/student.model';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  displayedColumns: string[] = ['id', 'subject', 'teacher', 'numberOfEnrolled', 'numberOfPlaces', 'actions'];
  dataSource = new MatTableDataSource<SubjectGroup>();
  groups: ISubjectGroup[] = [];
  student = new Student();

  constructor(private tokenStorage: TokenStorageService,
              private studentService: StudentService,
              private subjectGroupService: SubjectGroupService) { }

  ngOnInit(): void {
    const user = this.tokenStorage.getUser();
    this.studentService.getStudentByUsername(user.username).subscribe(result => {
      this.student = result;
      this.getAllGroupToRegistration(result.fieldOfStudyDTO.id);
    });
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<SubjectGroup>(this.groups);
    this.sortTable();
  }

  sortTable() {
    this.groups.sort((g1, g2) => {
      if (this.checkStudentInGroup(g1) === this.checkStudentInGroup(g2)) {
        return 0;
      } else if (this.checkStudentInGroup(g1)) {
        return -1;
      } else {
        return 1;
      }
    });
  }

  getAllGroupToRegistration(fieldOfStudyId: number) {
    this.subjectGroupService.getAllRegistration(fieldOfStudyId).subscribe(result => {
      this.groups = result;
      this.refreshTable();
    }, err => console.log(err));
  }

  checkStudentInGroup(group: ISubjectGroup) {
    return group.studentsDTO.filter(student => this.student.username === student.username).length > 0;
  }

  addToGroup(row: ISubjectGroup) {
    this.groups.splice(this.groups.indexOf(row), 1);
    row.studentsDTO.push(this.student);
    this.updateGroup(row);
  }

  removeFromGroup(row: ISubjectGroup) {
    this.groups.splice(this.groups.indexOf(row), 1);
    row.studentsDTO.splice(this.getIndexOfStudentInGroup(row), 1);
    this.updateGroup(row);
  }

  updateGroup(row: ISubjectGroup) {
    this.subjectGroupService.patchGroupToUpdateStudents(row).subscribe(result => {
      this.groups.push(result);
      this.refreshTable();
    }, err => console.log(err));
  }

  getIndexOfStudentInGroup(group: ISubjectGroup) {
    return group.studentsDTO.findIndex(student => student.username === this.student.username);
  }
}
