import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../../../dtos/user';

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.css']
})
export class ProfileInfoComponent implements OnInit {
  @Input() currentUser: User;
  constructor() { }

  ngOnInit(): void {
  }

}
