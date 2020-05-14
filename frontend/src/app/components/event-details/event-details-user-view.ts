import { Component, OnInit, Input } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {EventService} from '../../services/event.service';
import {SimpleEvent} from '../../dtos/simple-event';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details-user-view.html',
  styleUrls: ['./event-details-user-view.component.css']
})
export class EventDetailsUserViewComponent implements OnInit {

  e: SimpleEvent;

  constructor(private eventService: EventService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  //  this.events = null;
  }

  public loadEvent(): void {
    const eventCode = this.route.snapshot.paramMap.get('eventCode');
  //  this.eventService.getEventByEventCode(eventCode).subscribe(e => this.e = e);
  }

}
