import {Component, OnInit} from '@angular/core';
import {SubjectService} from '../subject.service';
import {ActivatedRoute} from '@angular/router';
import {Subject} from '../subject.model';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.scss']
})
export class SubjectDetailsComponent implements OnInit {
  subject = new Subject();

  constructor(private subjectService: SubjectService, private route: ActivatedRoute, private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getSubject(this.route.snapshot.paramMap.get('id'));
  }

  getSubject(id: string) {
    this.subjectService.getSubject(Number(id)).subscribe(result => {
      this.subject = result;
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }
}
