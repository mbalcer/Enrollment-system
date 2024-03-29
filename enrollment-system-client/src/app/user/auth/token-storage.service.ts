import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const ACTIVE_ROLE_KEY = 'auth-active-role';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor(private jwtHelper: JwtHelperService) {}

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }

  public saveActiveRole(role: string) {
    window.sessionStorage.setItem(ACTIVE_ROLE_KEY, role);
  }

  public getActiveRole() {
    return sessionStorage.getItem(ACTIVE_ROLE_KEY);
  }

  public isLogged() {
    this.isTokenExpired();
    const userKey = sessionStorage.getItem(USER_KEY);
    if (userKey == null) {
      return false;
    } else {
      return true;
    }
  }

  public isTokenExpired() {
    if (this.jwtHelper.isTokenExpired(this.getToken())) {
      this.signOut();
    }
  }

  public isRole(role: string) {
    let isRole = false;
    this.getUser().role.forEach(r => {
      if (r === role)
        isRole = true;
    });
    return isRole;
  }
}
