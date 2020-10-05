import {Component, OnInit} from '@angular/core';
import {User} from '../model/user.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  user = new User();

  constructor() { }

  ngOnInit(): void {
  }

}
