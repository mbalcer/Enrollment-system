import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../../../user/service/user.service';
import {IUser, User} from '../../../../user/model/user.model';
import {RoleService} from './role.service';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-change-role',
  templateUrl: './change-role.component.html',
  styleUrls: ['./change-role.component.scss']
})
export class ChangeRoleComponent implements OnInit {
  user: IUser = new User();
  roles: string[] = [];

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private roleService: RoleService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username');
    this.getUser(username);
  }

  getUser(username: string) {
    this.userService.getUserByUsername(username).subscribe(result => {
      this.user = result;
      this.getRoles();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  getRoles() {
    this.roleService.getAllRoles().subscribe(result => {
      this.roles = result;
      this.removeUserRoles();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  removeUserRoles() {
    this.user.roles.forEach(role => {
      if (this.roles.indexOf(role) != -1) {
        this.roles.splice(this.roles.indexOf(role), 1);
      }
    });
  }

  removeRole(role: string) {
    if (confirm("Are you sure you want to remove the role? This operation remove data typically for this role.")) {
      this.userService.removeRoleFromUser(this.user.username, role).subscribe(result => {
        this.user = result;
        this.notificationService.success("Remove role", "The role has been correctly removed from " + this.user.fullName);
        this.roles.push(role);
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    }
  }

  addRole(value: string) {
    this.userService.addRoleToUser(this.user.username, value).subscribe(result => {
      this.user = result;
      this.notificationService.success("Add role", "The role has been correctly added to " + this.user.fullName);
      if(this.roles.length == 1)
        this.removeUserRoles();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }
}
