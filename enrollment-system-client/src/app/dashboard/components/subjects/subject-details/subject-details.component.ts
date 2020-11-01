import {Component, OnInit} from '@angular/core';
import {SubjectService} from '../subject.service';
import {ActivatedRoute} from '@angular/router';
import {Subject} from '../subject.model';

@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.scss']
})
export class SubjectDetailsComponent implements OnInit {
  subject = new Subject();

  constructor(private subjectService: SubjectService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getSubject(this.route.snapshot.paramMap.get('id'));
  }

  getSubject(id: string) {
    this.subjectService.getSubject(Number(id)).subscribe(result => {
      this.subject = result;
    }, error => console.log(error));
  }
}
