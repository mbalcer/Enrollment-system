import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Faculty, IFaculty} from './faculty.model';
import {FacultyService} from './faculty.service';

@Component({
  selector: 'app-university',
  templateUrl: './university.component.html',
  styleUrls: ['./university.component.scss']
})
export class UniversityComponent implements OnInit {
  displayedColumnsFacultyTable: string[] = ['id', 'name', 'address', 'abbreviation', 'actions'];
  dataSource = new MatTableDataSource<Faculty>();
  faculties: IFaculty[];

  constructor(private facultyService: FacultyService) { }

  ngOnInit(): void {
    this.getFaculties();
  }

  refreshTable() {
    this.dataSource = new MatTableDataSource<Faculty>(this.faculties);
  }

  getFaculties() {
    this.facultyService.getAllFaculties().subscribe(result => {
      this.faculties = result;
      this.refreshTable();
    }, error => {
      console.log(error);
    });
  }

  deleteFaculty(row: IFaculty) {
    console.log(row);
  }

  editFaculty(row: IFaculty) {
    console.log(row);
  }

  addFaculty() {
    console.log("add");
  }
}
