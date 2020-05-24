import {Component, Input, OnInit} from '@angular/core';
import {ChangePassword} from '../../../../../dtos/change-password';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  @Input() changePassword: ChangePassword;
  constructor() { }

  ngOnInit(): void {
  }

}
