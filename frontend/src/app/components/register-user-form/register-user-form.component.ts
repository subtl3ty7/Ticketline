import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AuthRequest} from '../../dtos/auth-request';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';

@Component({
  selector: 'app-register-user-form',
  templateUrl: './register-user-form.component.html',
  styleUrls: ['./register-user-form.component.css']
})
export class RegisterUserFormComponent implements OnInit {
  postError = false;
  postErrorMessage = '';
  isNotValid = false;
  registrationForm: FormGroup;
  user: User;
  firstName = '';
  lastName = '';
  birthday: Date;
  password = '';
  email = '';
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: boolean = false;
  errorMessage: string = '';
  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.user = new User();
  }
  registerUser(form) {
    console.log(this.user);
    if (form.valid) {
      console.log('in onSubmit ', form.valid);
      this.userService.save(this.user).subscribe(
        result => console.log('success: ', result),
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

}
