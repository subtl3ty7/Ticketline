import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {MatDatepicker, MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import {UserService} from '../../services/user.service';
import {AuthRequest} from '../../dtos/auth-request';
import {User} from '../../dtos/user';
import * as bcrypt from 'bcryptjs';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // CustomError flag
  error;
  errorMessage: string = '';
  maxDate: Date;
  private user: User;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router, private background: Background) {
    this.background.defineBackground();
    const currentDate = new Date();
    this.maxDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDay());
    this.registrationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  registerUser() {
    this.submitted = true;
    if (this.registrationForm.valid) {
      console.log('Try to authenticate user: ' );
      this.user = new User();
      this.user.email = this.registrationForm.controls.email.value;
      this.user.firstName = this.registrationForm.controls.firstName.value;
      this.user.lastName = this.registrationForm.controls.lastName.value;
      this.user.birthday = new Date(this.registrationForm.controls.birthDate.value);
      this.user.password = this.registrationForm.controls.password.value;
      const salt = bcrypt.genSaltSync(10);
      const pwd = bcrypt.hashSync(this.user.password, salt);
      this.user.password = pwd;
      this.userService.save(this.user).subscribe(
        () => {
          console.log('Successfully registred user: ' + this.user.email);
          this.router.navigate(['/login']);
        },
        error1 => {
          console.log('Could not register due to:');
          console.log(error1);
          this.error = error1;
        }
      );
    } else {
      console.log('Invalid input');
    }
  }

  ngOnInit(): void {
  }

  defineBackground() {
    document.body.style.background = '#0c0d0f';
    document.body.style.backgroundImage = 'url("assets/images/bg.png")';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundPosition = 'top';
    document.body.style.backgroundSize = '100%';
  }
}
