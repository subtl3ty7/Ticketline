import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {SimpleEvent} from '../../../dtos/simple-event';
import {EventService} from '../../../services/event.service';

@Component({
  selector: 'app-top-ten-events-list',
  templateUrl: './top-events-list.component.html',
  styleUrls: ['./top-events-list.component.css']
})
export class TopEventsListComponent {
  @Input() events: SimpleEvent[];

  constructor() { }



}
