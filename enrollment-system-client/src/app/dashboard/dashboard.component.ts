import {Component, OnInit} from '@angular/core';
import {User} from '../user/model/user.model';
import {UserService} from '../user/service/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  user = new User();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.user.roles = [];
    this.userService.getUser().subscribe(result => {
      this.user = result;
    });
  }
}
