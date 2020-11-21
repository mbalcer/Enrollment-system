import {Component, OnInit} from '@angular/core';
import {SubjectGroupService} from '../groups/subject-group.service';
import {ISubjectGroup} from '../groups/subject-group.model';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {
  requests: ISubjectGroup[];

  constructor(private subjectGroupService: SubjectGroupService) { }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests() {
    this.subjectGroupService.getAllRequests().subscribe(result => {
      this.requests = result;
      console.log(this.requests);
    }, error => console.log(error));
  }

}
