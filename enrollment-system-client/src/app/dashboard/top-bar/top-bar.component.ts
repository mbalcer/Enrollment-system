import {Component, Input, OnInit} from '@angular/core';
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
export class TopBarComponent implements OnInit {

  @Input() user: IUser;

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

}
