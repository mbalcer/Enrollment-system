import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../../../user/service/user.service';
import {IUser, User} from '../../../../user/model/user.model';
import {RoleService} from './role.service';

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
              private roleService: RoleService) { }

  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username');
    this.getUser(username);
    this.getRoles();
  }

  getUser(username: string) {
    this.userService.getUserByUsername(username).subscribe(result => {
      this.user = result;
    }, error => console.log(error));
  }

  getRoles() {
    this.roleService.getAllRoles().subscribe(result => {
      this.roles = result;
    }, error => console.log(error));
  }

  removeRole(role: string) {
    if (confirm("Are you sure you want to remove the role? This operation remove data typically for this role.")) {
      this.userService.removeRoleFromUser(this.user.username, role).subscribe(result => {
        this.user = result;
        console.log("The role has been correctly removed from " + this.user.fullName);
      }, error => console.log(error));
    }
  }

  addRole(value: string) {
    this.userService.addRoleToUser(this.user.username, value).subscribe(result => {
      this.user = result;
      console.log("The role has been correctly added to " + this.user.fullName);
    }, error => console.log(error));
  }
}
