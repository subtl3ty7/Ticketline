import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../../../../../dtos/user';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {
  @Input() user: User;
  constructor() { }

  ngOnInit(): void {
  }

}
