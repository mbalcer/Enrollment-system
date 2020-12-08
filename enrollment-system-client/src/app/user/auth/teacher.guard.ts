import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {TokenStorageService} from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class TeacherGuard implements CanActivate {

  constructor(private router: Router, private tokenStorageService: TokenStorageService) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const isTeacher = this.tokenStorageService.isRole('TEACHER');
    if (isTeacher) {
      return true;
    } else {
      this.router.navigate(['/dashboard'], {queryParams: {returnUrl: state.url}});
    }

    return false;
  }
}
