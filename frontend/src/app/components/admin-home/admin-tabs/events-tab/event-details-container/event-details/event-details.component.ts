import { Component, OnInit } from '@angular/core';
import {EventDetailsWrapper, State} from './event-details-wrapper';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../../../../../services/event.service';
import {DetailedEvent} from '../../../../../../dtos/detailed-event';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {
  private ec: string;
  private sub: any;
  public wrapper = new EventDetailsWrapper();
  private state = State;
  public name: string;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private eventService: EventService) { }

  ngOnInit(): void {
    this.wrapper.state = this.state.READY;
    this.loadEvent();
  }
  private loadEvent() {
    this.sub = this.route.params.subscribe(params => {
      this.ec = params['ec'];
    });
    if (this.ec) {
      this.eventService.getDetailedEventByUserCode(this.ec).subscribe(
        (event: DetailedEvent) => {
          Object.assign(this.wrapper.model, event);
          this.name = this.wrapper.model.name;
        },
        error => {
          this.router.navigate(['administration', 'events']);
        }
      );
    }
  }
  public eventsNavigate() {
    if (this.wrapper.state !== this.state.PROCESSING) {
      this.router.navigate(['administration', 'events']);
    }
  }


}
