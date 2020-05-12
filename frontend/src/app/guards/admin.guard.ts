import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService,
              private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      if( this.authService.getUserRole() === 'USER') {
        this.router.navigate(['/customer-home']);
        return false;
      }
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
