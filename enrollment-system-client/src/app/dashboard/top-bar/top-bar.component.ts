import {Component, Input, OnInit} from '@angular/core';
import {IUser} from '../../model/user.model';
import {MatIconRegistry} from '@angular/material/icon';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {

  @Input() user: IUser;

  constructor(private iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon('signout', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/signout.svg'));
  }

  ngOnInit(): void {
  }

}
