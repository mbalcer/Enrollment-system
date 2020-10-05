import {Component, Input, OnInit} from '@angular/core';
import {IUser} from '../../model/user.model';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {

  @Input() user: IUser;

  constructor() { }

  ngOnInit(): void {
  }

}
