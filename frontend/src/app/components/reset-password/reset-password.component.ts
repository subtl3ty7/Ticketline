import { Component, OnInit } from '@angular/core';
import {AuthRequest} from '../../dtos/auth-request';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  formResetPassword: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // CustomError flag
  error: boolean = false;
  errorMessage: string = '';
  processing: boolean = false;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router, private background: Background) {
    this.background.defineBackground();
    this.formResetPassword = this.formBuilder.group({
      email: ['', [Validators.required]],
    });
  }

  /**
   * Form validation will start after the method is called, additionally an ResetPasswordRequest will be sent
   */
  sendResetPasswordRequest() {
    console.log('Sending reset password request to: ' + this.formResetPassword.controls.email.value);
    this.submitted = true;
    this.processing = true;
    if (this.formResetPassword.valid) {
      this.userService.resetPasswordRequest(this.formResetPassword.controls.email.value).subscribe(
        () => {
          console.log('Successfully sent request to email: ' + this.formResetPassword.controls.email.value);
          this.processing = false;
         this.router.navigate(['/home']);
          document.body.style.backgroundImage = null;
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
  }

  /**
   * CustomError flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  ngOnInit() {
  }

  defineBackground() {
    document.body.style.background = '#0c0d0f';
    document.body.style.backgroundImage = 'url("assets/images/bg.png")';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundPosition = 'top';
    document.body.style.backgroundSize = '100%';
  }
}
