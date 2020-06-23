import { Component, OnInit } from '@angular/core';
import {ChangePassword} from '../../../../../../dtos/change-password';
import * as bcrypt from 'bcryptjs';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../../../../services/user.service';
import {User} from '../../../../../../dtos/user';

@Component({
  selector: 'app-reset-password-admin',
  templateUrl: './reset-password-admin.component.html',
  styleUrls: ['./reset-password-admin.component.css']
})
export class ResetPasswordAdminComponent implements OnInit {

  private changePassword = new ChangePassword();
  error;
  sub;
  uc: string;
  email: string;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService) { }

  ngOnInit(): void {
    this.loadUser();
  }

  private loadUser() {
    this.sub = this.route.params.subscribe(params => {
      this.uc = params['uc'];
    });
    if (this.uc) {
      this.userService.getUserByUserCode(this.uc).subscribe(
        (user: User) => {
          this.email = user.email;
        },
        (error) => {
          this.router.navigate(['administration']);
        }
      );
    }
  }

  public usersNavigate() {
    this.router.navigate(['administration']);
  }

  savePassword() {
    if (this.changePassword.newPassword) {
      const salt = bcrypt.genSaltSync(10);
      const pwd = bcrypt.hashSync(this.changePassword.newPassword, salt);
      this.changePassword.newPassword = pwd;
      this.changePassword.email = this.email;
      this.userService.changePasswordCustomer(this.changePassword).subscribe(() => {
          this.router.navigate(['administration']);
        },
        (error) => {
          this.error = error;
        });
    }
  }

  closePasswordMode() {
    this.usersNavigate();
  }

}
