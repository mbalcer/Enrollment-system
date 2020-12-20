import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {IUser, User} from '../../../user/model/user.model';
import {UserService} from '../../../user/service/user.service';
import {MatPaginator} from '@angular/material/paginator';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  displayedColumns: string[] = ['username', 'email', 'fullName', 'role', 'active', 'actions'];
  dataSource = new MatTableDataSource<User>();
  users: IUser[];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private userService: UserService, private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers() {
    this.userService.getAllUsers().subscribe(result => {
      this.users = result;
      this.refreshTables();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  refreshTables() {
    this.dataSource = new MatTableDataSource<User>(this.users);
    this.dataSource.paginator = this.paginator;
  }

  filterTable(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
