import {AfterViewChecked, Component, Input, OnInit} from '@angular/core';
import {IUser} from '../../user/model/user.model';
import {MatIconRegistry} from '@angular/material/icon';
import {DomSanitizer} from '@angular/platform-browser';
import {TokenStorageService} from '../../user/auth/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit, AfterViewChecked {

  @Input() user: IUser;
  buttonChangeRole: any = {
    student: false,
    teacher: false,
    admin: false,
    init: false
  };

  constructor(private iconRegistry: MatIconRegistry, sanitizer: DomSanitizer,
              private tokenStorage: TokenStorageService,
              private router: Router) {
    iconRegistry.addSvgIcon('signout', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/signout.svg'));
  }

  ngOnInit(): void {
  }

  logout() {
    this.tokenStorage.signOut();
    this.router.navigateByUrl('/logout');
  }

  ngAfterViewChecked(): void {
    if (this.user.username !== undefined && !this.buttonChangeRole.init) {
      this.setChangeRoleButtons();
      this.buttonChangeRole.init = true;
    }
  }

  setChangeRoleButtons() {
    const activeRole = this.tokenStorage.getActiveRole();
    const otherRoles = this.user.roles.filter(r => r != activeRole);
    if (otherRoles.length > 0) {
      otherRoles.forEach(r => {
        if (r == 'STUDENT') {
          this.buttonChangeRole.student = true;
        } else if (r == 'TEACHER') {
          this.buttonChangeRole.teacher = true;
        } else if (r == 'ADMIN') {
          this.buttonChangeRole.admin = true;
        }
      });
    }
  }

  changeActiveRole(role: string) {
    this.tokenStorage.saveActiveRole(role);
    window.location.reload();
  }
}
