import {Component, Input, OnInit} from '@angular/core';
import {MyProfileWrapper} from '../../../my-profile-wrapper';
import {Form, FormControl} from '@angular/forms';
import {MatDatepicker, MatDatepickerInputEvent} from '@angular/material/datepicker';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  @Input() wrapper: MyProfileWrapper;
  date: FormControl;
  matDatePicker: MatDatepicker<any>;
  constructor() {
  }

  ngOnInit(): void {
    this.date = new FormControl(this.wrapper.model.birthday);
  }
  addEvent(type: string, event: MatDatepickerInputEvent<any>) {
    this.wrapper.model.birthday = event.value;
  }
}
