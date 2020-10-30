import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {IUser, User} from '../../../model/user.model';
import {UserService} from './user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  displayedColumns: string[] = ['username', 'email', 'fullName', 'role', 'active'];
  dataSource = new MatTableDataSource<User>();
  users: IUser[];
  roles = ['ALL', 'STUDENT', 'TEACHER', 'ADMIN'];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers() {
    this.userService.getAllUsers().subscribe(result => {
      this.users = result;
      this.refreshTables();
    }, error => console.log(error));
  }


  refreshTables() {
    this.dataSource = new MatTableDataSource<User>(this.users);
  }

  filterTable(filterValue) {
    if (filterValue.isUserInput) {
      filterValue = filterValue.source.value;
      if (filterValue === 'ALL') {
        this.dataSource.filter = '';
      } else {
        this.dataSource.filter = filterValue;
      }
    }
  }
}
