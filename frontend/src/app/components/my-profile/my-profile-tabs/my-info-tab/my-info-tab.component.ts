import {Component, OnInit} from '@angular/core';
import {User} from '../../../../dtos/user';
import {MyProfileWrapper, State} from '../../my-profile-wrapper';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../../services/user.service';
import {DeleteAccountComponent} from './delete-account/delete-account.component';
import {MatDialog} from '@angular/material/dialog';
import {ChangePassword} from '../../../../dtos/change-password';
import * as bcrypt from 'bcryptjs';


@Component({
  selector: 'app-my-info-tab',
  templateUrl: './my-info-tab.component.html',
  styleUrls: ['./my-info-tab.component.css']
})
export class MyInfoTabComponent implements OnInit {
  public wrapper = new MyProfileWrapper();
  private backupProfile = new User();
  private state = State;
  private changePassword = new ChangePassword();
  public firstName: string;
  public lastName: string;
  error;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.wrapper.state = this.state.READY;
    this.loadUser();
  }

  private loadUser() {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        Object.assign(this.wrapper.model, user);
        this.firstName = this.wrapper.model.firstName;
        this.lastName = this.wrapper.model.lastName;
        this.wrapper.model.birthday = new Date(this.wrapper.model.birthday);
      },
      (error) => {
        this.error = error.error;
      }
    );
  }
  openEditMode() {
    this.backupProfile = JSON.parse(JSON.stringify(this.wrapper.model));
    this.wrapper.state = this.state.EDIT;
  }

  closeEditMode() {
    Object.assign(this.wrapper.model, this.backupProfile);
    this.wrapper.state = this.state.READY;
  }

  saveUserInfo() {
    if (this.wrapper.model.firstName && this.wrapper.model.lastName && this.wrapper.model.email && this.wrapper.model.birthday) {
      this.userService.update(this.wrapper.model).subscribe(() => {
        this.wrapper.state = this.state.READY;
      });
    }
  }

  openDeleteDialog() {
    this.wrapper.state = State.DELETING;
    const dialogRef = this.dialog.open(DeleteAccountComponent,
      {data: this.wrapper});
    dialogRef.afterClosed().subscribe(() => {
      this.wrapper.state = State.READY;
    });
  }

  openPasswordMode() {
    this.wrapper.state = this.state.PASSWORD;
  }

  savePassword() {
    if (this.changePassword.newPassword) {
      const salt = bcrypt.genSaltSync(10);
      const pwd = bcrypt.hashSync(this.changePassword.newPassword, salt);
      this.changePassword.newPassword = pwd;
      this.changePassword.email = this.wrapper.model.email;
      this.userService.changePasswordCustomer(this.changePassword).subscribe(() => {
        this.wrapper.state = this.state.READY;
      },
        (error) => {
        this.error = error.error;
        });
    }
  }

  closePasswordMode() {
    this.changePassword = new ChangePassword();
    this.wrapper.state = this.state.READY;
  }
}
