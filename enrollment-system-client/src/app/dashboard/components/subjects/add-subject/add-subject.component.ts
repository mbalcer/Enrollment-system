import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationsService} from 'angular2-notifications';
import {SubjectService} from '../subject.service';
import {Subject} from '../subject.model';
import {NgForm} from '@angular/forms';
import {AngularEditorConfig} from '@kolkov/angular-editor';

@Component({
  selector: 'app-add-subject',
  templateUrl: './add-subject.component.html',
  styleUrls: ['./add-subject.component.scss']
})
export class AddSubjectComponent implements OnInit {
  isAdd = true;
  subjectToEdit = new Subject();
  courseTypes = ['LECTURE', 'DISCUSSIONS', 'LAB', 'PROJECT', 'SEMINAR'];
  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    placeholder: 'Enter the news description...',
    toolbarHiddenButtons: [
      [
        'undo',
        'redo',
        'subscript',
        'superscript',
        'indent',
        'outdent',
        'unlink',
        'insertImage',
        'insertVideo',
      ]
    ]
  };

  constructor(private route: ActivatedRoute,
              private subjectService: SubjectService,
              private router: Router,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
      this.isAdd = false;
      this.getSubject(Number(id));
    }
  }

  getSubject(id: number) {
    this.subjectService.getSubject(id).subscribe(result => {
      this.subjectToEdit = result;
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  saveSubject(f: NgForm) {
    if (this.isAdd) {
      this.subjectService.postSubject(this.subjectToEdit).subscribe(result => {
        this.notificationService.success("Add subject", "You added the new subject with name " + result.name);
        this.subjectToEdit = new Subject();
        f.resetForm();
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    } else {
      this.subjectService.putSubject(this.subjectToEdit, this.subjectToEdit.id).subscribe(result => {
        this.notificationService.success("Update subject", "You updated the subject with name " + result.name);
        this.subjectToEdit = new Subject();
        this.isAdd = true;
        f.resetForm();
        this.router.navigateByUrl('/dashboard/subjects/add');
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    }
  }
}
