import { Component, OnInit, Input } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {EventService} from '../../services/event.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Background} from '../../utils/background';
import {Show} from '../../dtos/show';

@Component({
  selector: 'app-event-details-user-view',
  templateUrl: './event-details-user-view.html',
  styleUrls: ['./event-details-user-view.component.css']
})
export class EventDetailsUserViewComponent implements OnInit {
  error;
  event: DetailedEvent;

  constructor(private eventService: EventService,
              private router: Router,
              private route: ActivatedRoute,
              private background: Background) {
    this.background.defineBackground();
  }

  ngOnInit(): void {
    this.loadEvent();
  }

  public loadEvent(): void {
    const eventCode = this.route.snapshot.paramMap.get('eventCode');
    this.eventService.getDetailedEventByUserCode(eventCode).subscribe(
        (event: DetailedEvent) => {
          this.event = event;
        },
        (error) => {
          this.error = error;
        }
    );
  }

  openPurchase(show: Show) {
    if (show) {
      console.log('Show is:');
      console.log(show);
      // sessionStorage.clear();
      // sessionStorage.setItem('show', JSON.stringify(show));
      this.router.navigate(['ticket-purchase/' + show.id]);
    }
  }
}
