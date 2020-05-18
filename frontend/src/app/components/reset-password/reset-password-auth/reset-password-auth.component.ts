import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserLogin} from '../../../dtos/user-login';
import {AuthService} from '../../../services/auth.service';
import {AuthRequest} from '../../../dtos/auth-request';

@Component({
  selector: 'app-reset-password-auth',
  templateUrl: './reset-password-auth.component.html',
  styleUrls: ['./reset-password-auth.component.css']
})
export class ResetPasswordAuthComponent implements OnInit {
  error: boolean = false;
  errorMessage: string = '';
  private resetPasswordCode: string;
  constructor(private userService: UserService,
              public  router: Router,
              private route: ActivatedRoute,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      if (!param['rc']) {
        this.router.navigate(['/home']);
      }
      this.resetPasswordCode = param['rc'];
    });
    this.userService.resetPasswordAuth(this.resetPasswordCode).subscribe(
      (userLogin: UserLogin) => {
        const authRequest: AuthRequest = new AuthRequest(userLogin.email, 'Password');
        this.authenticateUser(authRequest);
      },
      error => {
        console.log('Could not send request due to:');
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      }
    );
  }

  authenticateUser(authRequest: AuthRequest) {
    console.log('Try to authenticate user: ' + authRequest.email);
    this.authService.loginUser(authRequest).subscribe(
      () => {
        console.log('Successfully logged in user: ' + authRequest.email);
        this.router.navigate(['/home']);
        document.body.style.backgroundImage = null;
      },
      error => {
        console.log('Could not log in due to:');
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      }
    );
  }

}
