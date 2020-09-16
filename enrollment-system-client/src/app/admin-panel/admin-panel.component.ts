import { Component, OnInit } from '@angular/core';
import {AuthService} from '../login/auth/auth.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  username = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.username = this.authService.getLoggedInUserName();
  }

  logout() {
    this.authService.logout();
  }
}
