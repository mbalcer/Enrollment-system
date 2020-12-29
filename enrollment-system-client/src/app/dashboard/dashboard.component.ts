import {Component, OnInit} from '@angular/core';
import {User} from '../user/model/user.model';
import {UserService} from '../user/service/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  user = new User();

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.user.roles = [];
    this.userService.getUser().subscribe(result => {
      this.user = result;
      if(!this.user.fullName) {
        this.router.navigateByUrl("/dashboard/profile/edit");
      }
    });
  }
}
