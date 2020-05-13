import { Component, OnInit, Input } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {EventService} from '../../services/event.service';
import {SimpleEvent} from '../../dtos/simple-event';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  events: SimpleEvent[];
  @Input() e: SimpleEvent;

  constructor(private eventService: EventService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  //  this.loadEvent();
  }

  public loadEvent(): void {
    const eventCode = this.route.snapshot.paramMap.get('eventCode');
    this.eventService.getEventByEventCode(eventCode).subscribe(e => this.e = e);
  }

}
