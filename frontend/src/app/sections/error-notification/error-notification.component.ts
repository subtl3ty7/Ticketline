import {Component, Input, OnInit} from '@angular/core';
import {BackendError} from '../../dtos/backend-error';

@Component({
  selector: 'app-error-notification',
  templateUrl: './error-notification.component.html',
  styleUrls: ['./error-notification.component.css']
})
export class ErrorNotificationComponent implements OnInit {
  @Input() backendError: BackendError;
  @Input() errorMessage: string;

  constructor() { }

  ngOnInit(): void {
  }

}
