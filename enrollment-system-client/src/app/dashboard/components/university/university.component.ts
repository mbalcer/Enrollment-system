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

  facultyToSave = new Faculty();

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
    this.facultyService.deleteFaculty(row.id).subscribe(result => {
      this.faculties.splice(this.faculties.indexOf(row), 1);
      this.refreshTable();
    }, error => {
      console.log(error);
    });
  }

  editFaculty(row: IFaculty) {
    this.facultyToSave = row;
  }

  saveFaculty() {
    if (this.facultyToSave.id != null && typeof this.facultyToSave.id === 'number') {
      this.facultyService.putFaculty(this.facultyToSave).subscribe(result => {
        this.facultyToSave = new Faculty();
      }, error => {
        console.log(error);
      });
    } else {
      this.facultyService.postFaculty(this.facultyToSave).subscribe(result => {
        this.faculties.push(result);
        this.facultyToSave = new Faculty();
        this.refreshTable();
      }, error => {
        console.log(error);
      });
    }
  }
}
