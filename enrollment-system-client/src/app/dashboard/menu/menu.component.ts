import {AfterViewChecked, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {MatIconRegistry} from '@angular/material/icon';
import {IUser} from '../../model/user.model';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit, AfterViewChecked {

  @Input() user: IUser;

  menu = [];

  constructor(private iconRegistry: MatIconRegistry, sanitizer: DomSanitizer, private cdr: ChangeDetectorRef) {
    iconRegistry.addSvgIcon('registration', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/web.svg'));
    iconRegistry.addSvgIcon('subjects', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/book.svg'));
    iconRegistry.addSvgIcon('profile', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/user.svg'));
    iconRegistry.addSvgIcon('home', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/home.svg'));
    iconRegistry.addSvgIcon('university', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/university.svg'));
    iconRegistry.addSvgIcon('groups', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/class.svg'));
    iconRegistry.addSvgIcon('requests', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/interview.svg'));
    iconRegistry.addSvgIcon('settings', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/settings.svg'));
    iconRegistry.addSvgIcon('users', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/group.svg'));

  }

  ngOnInit(): void {
  }

  getMenuForRole(role: string) {
    let menu = [];
    if (role === 'STUDENT') {
      menu = ['registration', 'subjects', 'profile'];
    } else if (role === 'TEACHER') {
      menu = ['groups', 'profile'];
    } else if (role === 'ADMIN') {
      menu = ['university', 'groups', 'users', 'requests', 'settings', 'profile'];
    }

    return menu;
  }

  ngAfterViewChecked(): void {
    if (this.menu.length === 0) {
      this.menu = this.getMenuForRole(this.user.role);
      this.cdr.detectChanges();
    }
  }
}
