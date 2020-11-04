import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../subject-group.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SubjectGroup} from '../subject-group.model';
import {ISubject} from '../../subjects/subject.model';
import {ITeacher} from '../../../../user/model/teacher.model';
import {SubjectService} from '../../subjects/subject.service';
import {TeacherService} from '../../../../user/service/teacher.service';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.component.html',
  styleUrls: ['./add-group.component.scss']
})
export class AddGroupComponent implements OnInit {
  groupToAdd = new SubjectGroup();
  isAdd = true;
  subjects: ISubject[];
  teachers: ITeacher[];

  constructor(private subjectGroupService: SubjectGroupService,
              private subjectService: SubjectService,
              private teacherService: TeacherService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
      this.isAdd = false;
      this.subjectGroupService.getGroup(Number(id)).subscribe(result => {
        this.groupToAdd = result;
        console.log(this.groupToAdd);
      }, error => console.log(error));
    }

    this.subjectService.getAllSubjects().subscribe(result => {
      this.subjects = result;
    }, error => console.log(error));

    this.teacherService.getAllTeachers().subscribe(result => {
      this.teachers = result;
    }, error => console.log(error));
  }

  saveGroup() {
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

}
