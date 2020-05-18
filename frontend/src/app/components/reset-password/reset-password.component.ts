import { Component, OnInit } from '@angular/core';
import {AuthRequest} from '../../dtos/auth-request';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  formResetPassword: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: boolean = false;
  errorMessage: string = '';

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) {
    this.formResetPassword = this.formBuilder.group({
      email: ['', [Validators.required]],
    });
    document.body.style.backgroundImage = 'url("assets/images/bg.png")';
    document.body.style.backgroundColor = '#0c0d0f';
  }

  /**
   * Form validation will start after the method is called, additionally an ResetPasswordRequest will be sent
   */
  sendResetPasswordRequest() {
    console.log('Sending reset password request to: ' + this.formResetPassword.controls.email.value);
    this.submitted = true;
    if (this.formResetPassword.valid) {
      this.userService.resetPasswordRequest(this.formResetPassword.controls.email.value).subscribe(
        () => {
          console.log('Successfully sent request to email: ' + this.formResetPassword.controls.email.value);
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
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  ngOnInit() {
  }
}
