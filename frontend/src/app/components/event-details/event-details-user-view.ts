import { Component, OnInit, Input } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {EventService} from '../../services/event.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Show} from '../../dtos/show';
import {EventLocation} from '../../dtos/event-location';

@Component({
  selector: 'app-event-details-user-view',
  templateUrl: './event-details-user-view.html',
  styleUrls: ['./event-details-user-view.component.css']
})
export class EventDetailsUserViewComponent implements OnInit {

  @Input() event: DetailedEvent;

  constructor(private eventService: EventService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadEvent();
  }

  public loadEvent(): void {
    const eventCode = this.route.snapshot.paramMap.get('eventCode');
    this.eventService.getDetailedEventByUserCode(eventCode).subscribe(e => this.event = e);
  }

}
