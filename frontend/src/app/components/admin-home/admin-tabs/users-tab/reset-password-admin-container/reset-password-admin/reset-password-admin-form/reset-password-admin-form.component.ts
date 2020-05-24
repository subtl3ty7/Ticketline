import {Component, Input, OnInit} from '@angular/core';
import {ChangePassword} from '../../../../../../../dtos/change-password';

@Component({
  selector: 'app-reset-password-admin-form',
  templateUrl: './reset-password-admin-form.component.html',
  styleUrls: ['./reset-password-admin-form.component.css']
})
export class ResetPasswordAdminFormComponent implements OnInit {
  @Input() changePassword: ChangePassword;
  constructor() { }

  ngOnInit(): void {
  }

}
