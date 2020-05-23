import {Component, Input, OnInit} from '@angular/core';
import {CustomError} from '../../dtos/customError';

@Component({
  selector: 'app-error-notification',
  templateUrl: './error-notification.component.html',
  styleUrls: ['./error-notification.component.css']
})
export class ErrorNotificationComponent implements OnInit {
  @Input() error: CustomError;

  constructor() { }

  ngOnInit(): void {
    this.checkForBadError();
  }

  vanishError() {
    this.error = null;
  }

  checkForBadError() {
    if (!this.error || (!this.error.status && !this.error.error && (!this.error.messages || !this.error.messages[0]))) {
      this.error.error = 'Invalid Error';
      this.error.messages = ['Backend not reachable, bad Request or bad Error Message'];
    }
  }

}
