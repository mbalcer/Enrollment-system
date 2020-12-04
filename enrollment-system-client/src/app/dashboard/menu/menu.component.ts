import {AfterViewChecked, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {MatIconRegistry} from '@angular/material/icon';
import {IUser} from '../../user/model/user.model';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit, AfterViewChecked {

  @Input() user: IUser;

  menu: MenuViewModel[] = [];

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
    let menu: MenuViewModel[] = [];
    if (role === 'STUDENT') {
      menu = [
        { routerLink: ['registration'], svgIcon: 'registration', name: 'Registration' },
        { routerLink: ['subjects'], svgIcon: 'subjects', name: 'Subjects'}
      ];
    } else if (role === 'TEACHER') {
      menu = [
        { routerLink: ['my-groups'], svgIcon: 'groups', name: 'My Groups'},
        { routerLink: ['groups', 'add'], fontAwesomeIcon: 'add_circle_outline', name: 'Add group'}
      ];
    } else if (role === 'ADMIN') {
      menu = [
        { routerLink: ['university'], svgIcon: 'university', name: 'University' },
        { routerLink: ['groups'], svgIcon: 'groups', name: 'Groups' },
        { routerLink: ['users'], svgIcon: 'users', name: 'Users' },
        { routerLink: ['requests'], svgIcon: 'requests', name: 'Requests' },
        { routerLink: ['settings'], svgIcon: 'settings', name: 'Settings' }
      ];
    }

    if (menu.length !== 0) {
      menu = menu.concat({routerLink: ['profile'], svgIcon: 'profile', name: 'Profile'});
    }

    return menu;
  }

  ngAfterViewChecked(): void {
    if (this.menu.length === 0 && this.user.roles.length !== 0) {
      const firstRole = this.user.roles[0];
      this.menu = this.getMenuForRole(firstRole);
      this.cdr.detectChanges();
    }
  }
}

export interface MenuViewModel {
  routerLink: string[];
  svgIcon?: string;
  fontAwesomeIcon?: string;
  name: string;
}
