import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {IUser, User} from '../../../model/user.model';
import {UserService} from './user.service';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  displayedColumns: string[] = ['username', 'email', 'fullName', 'role', 'active'];
  dataSource = new MatTableDataSource<User>();
  users: IUser[];
  roles = ['STUDENT', 'TEACHER', 'ADMIN'];
  filterRoles = ['ALL'].concat(this.roles);
  editableRow = null;
  disableEditRow = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;

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
    this.dataSource.paginator = this.paginator;
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

  changeRole(element: IUser) {
    this.userService.changeRole(element).subscribe(result => {
      this.refreshTables();
    }, error => console.log(error));
    this.cancelChangeRole();
  }

  displayEditableRow(element: IUser) {
    if (this.editableRow === null && !this.disableEditRow) {
      this.editableRow = element.username;
    }
    if (this.disableEditRow) {
      this.disableEditRow = false;
    }
  }

  cancelChangeRole() {
    this.editableRow = null;
    this.disableEditRow = true;
  }
}
