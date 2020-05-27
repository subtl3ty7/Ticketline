import {Component, Directive, EventEmitter, HostBinding, Input, OnInit, Output} from '@angular/core';
import {CustomError} from '../../dtos/customError';

@Component({
  selector: 'app-error-notification',
  templateUrl: './error-notification.component.html',
  styleUrls: ['./error-notification.component.css']
})
export class ErrorNotificationComponent implements OnInit {
  @Input() error;
  @Output() newError: EventEmitter<any> = new EventEmitter<any>();
  customError: CustomError;

  constructor() { }

  ngOnInit(): void {
    console.log(this.error);
    this.getError();
  }

  vanishError() {
    this.error = null;
    this.customError = null;
    this.newError.emit(null);
  }

  getError() {
    this.customError = new CustomError();
    const error = this.error.error;
    if (error && (error.status || error.error || (error.messages && error.messages[0]))) {
        this.customError = error;
    } else if (this.error && (this.error.status || this.error.error || (this.error.messages && this.error.messages[0]))) {
      this.customError.status = this.error.status;
      this.customError.error = this.error.error;
      this.customError.messages = this.error.messages;
    } else if (this.error && this.error.status) {
      this.customError.status = this.error.status;
      this.customError.error = 'Keine Antwort';
      this.customError.messages = ['Backend antwortet nicht oder sendet ungültige Fehlermeldung'];
    } else {
      this.customError.error = 'Keine Antwort';
      this.customError.messages = ['Backend antwortet nicht oder sendet ungültige Fehlermeldung'];
    }
  }
}
