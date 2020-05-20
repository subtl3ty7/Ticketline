import {Component, Input, OnInit} from '@angular/core';
import {BackendError} from '../../dtos/backend-error';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  @Input() error: BackendError;

  constructor() {}

  ngOnInit(): void {
    this.checkForBadError();
  }

  checkForBadError() {
    console.log('Log Error: ' + this.error.status);
    if (!this.error || !this.error.status || !this.error.error || !this.error.messages || !this.error.messages[0]) {
      this.error.status = 0;
      this.error.error = 'Invalid Backend Error';
      this.error.messages = ['Backend not reachable or bad error message'];
    }
  }

}
