import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../../../dtos/user';
import {MatDatepicker, MatDatepickerInputEvent} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-create-user-form',
  templateUrl: './create-user-form.component.html',
  styleUrls: ['./create-user-form.component.css']
})
export class CreateUserFormComponent implements OnInit {
  @Input() user: User;
  date = new FormControl(new Date());
  maxDate: Date;
  matDatePicker: MatDatepicker<any>;
  adminFlag = false;
  constructor() {
    const currentDate = new Date();
    this.maxDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDay());
  }

  ngOnInit(): void {
    this.user.admin = false;
  }
  changeUserType() {
    this.user.admin = this.adminFlag;
  }
  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    this.user.birthday = event.value;
  }
}
