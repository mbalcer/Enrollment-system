import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {TokenStorageService} from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class StudentGuard implements CanActivate {

  constructor(private router: Router, private tokenStorageService: TokenStorageService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const isStudent = this.tokenStorageService.isRole('STUDENT');
    if (isStudent) {
      return true;
    } else {
      this.router.navigate(['/dashboard'], {queryParams: {returnUrl: state.url}});
    }

    return false;
  }

}
