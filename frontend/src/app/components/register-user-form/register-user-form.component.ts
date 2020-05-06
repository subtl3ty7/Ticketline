import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AuthRequest} from '../../dtos/auth-request';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';
import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-register-user-form',
  templateUrl: './register-user-form.component.html',
  styleUrls: ['./register-user-form.component.css']
})
export class RegisterUserFormComponent implements OnInit {
  postError = false;
  postErrorMessage = '';
  isNotValid = false;
  user: User;
  firstName = '';
  lastName = '';
  birthday = '';
  password = '';
  passwordConfirm = '';
  email = '';
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: boolean = false;
  errorMessage: string = '';
  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.user = new User(null, '', this.firstName, this.lastName, this.email,
      this.password, this.birthday, '', '', false, false,
      false, 0, 'CUSTOMER', false);
  }
  registerUser(form) {
    console.log(this.user.password);
    this.submitted = true;
    const salt = bcrypt.genSaltSync(10);
    const pwd = bcrypt.hashSync(this.user.password, salt);
    if (form.valid) {
      console.log('in onSubmit ', form.valid);
      this.user.password = pwd;
      this.userService.save(this.user).subscribe(
        result => {
          console.log('success: ', result),
            this.router.navigate(['/login']);
        },
        error => this.onHttpError(error)
      );
    } else {
      this.postError = true;
      this.postErrorMessage = 'Please fix errors';
    }
  }
  onHttpError(errorResponse: any) {
    console.log('error ', errorResponse);
    this.postErrorMessage = errorResponse.error.errorMessage;
    this.postError = true;
  }
  vanishError() {
    this.postError = false;
  }
}
