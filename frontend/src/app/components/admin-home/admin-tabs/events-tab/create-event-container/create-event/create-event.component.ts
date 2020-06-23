import { Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../../../../../services/event.service';
import {CreateEventWrapper} from './create-event-wrapper';
import {Show} from '../../../../../../dtos/show';
import {EventLocation} from '../../../../../../dtos/event-location';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {

  public wrapper = new CreateEventWrapper();
  error;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private eventService: EventService) { }

  ngOnInit(): void {
  }
  public eventsNavigate() {
    this.router.navigate(['administration', 'events']);
  }

  cancelCreateEventMode() {
    this.eventsNavigate();
  }
  saveEvent() {
    if (this.wrapper.valid()) {
      this.setPrices();
      this.setEventDates();
      this.eventService.save(this.wrapper.model).subscribe(
        (next) => {
          this.router.navigate(['administration', 'events']);
        },
        (error) => {
          this.error = error;
        }
      );
    }
  }

  private setPrices() {
    this.wrapper.model.prices = [];
    const prices: number[] = [];
    this.wrapper.model.shows.forEach(function(show) {
      show.eventLocation.sections.forEach(function (section) {
        const price = show.price + section.price;
        if (!prices.includes(price)) {
          prices.push(show.price + section.price);
        }
      });
    });
    this.wrapper.model.prices = prices.sort();
    this.wrapper.model.startPrice = this.wrapper.model.prices[0];
  }

  private setEventDates() {
    const startTimes: Date[] = [];
    const endTimes: Date[] = [];
    this.wrapper.model.shows.forEach(function(show) {
      startTimes.push(show.startsAt);
      endTimes.push(show.endsAt);
    });
    startTimes.sort((a: Date, b: Date) => {
      return (a.getTime() - b.getTime());
    });
    this.wrapper.model.startsAt = startTimes[0];
    endTimes.sort((a: Date, b: Date) => {
      return (a.getTime() - b.getTime());
    });
    this.wrapper.model.endsAt = endTimes[endTimes.length - 1];
  }

}
