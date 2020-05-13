import { Injectable } from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class NotLoggedInGuard implements CanActivate {

  constructor(private authService: AuthService,
              private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      if (this.authService.getUserRole() === 'ADMIN') {
        this.router.navigate(['/administration']);
      } else {
        this.router.navigate(['/home']);
      }
      return false;
    } else {
      return true;
    }
  }
}
