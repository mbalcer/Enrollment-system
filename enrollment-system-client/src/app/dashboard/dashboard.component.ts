import {Component, OnInit} from '@angular/core';
import {User} from '../model/user.model';
import {AccountService} from '../service/account.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  user = new User();

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.accountService.getUser().subscribe(result => {
      this.user = result;
    });
  }
}
