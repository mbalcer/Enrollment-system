import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {StudentService} from '../../../user/service/student.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {ISubjectGroup} from '../groups/subject-group.model';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  groups: ISubjectGroup[] = [];

  constructor(private tokenStorage: TokenStorageService,
              private studentService: StudentService,
              private subjectGroupService: SubjectGroupService) { }

  ngOnInit(): void {
    const user = this.tokenStorage.getUser();
    this.studentService.getStudentByUsername(user.username).subscribe(result => this.getAllGroupToRegistration(result.fieldOfStudyDTO.id));
  }

  getAllGroupToRegistration(fieldOfStudyId: number) {
    this.subjectGroupService.getAllRegistration(fieldOfStudyId).subscribe(result => {
      this.groups = result;
    }, err => console.log(err));
  }

}
