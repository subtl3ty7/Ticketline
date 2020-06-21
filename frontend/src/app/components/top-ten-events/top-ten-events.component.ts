import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {SimpleEvent} from '../../dtos/simple-event';
import {EventService} from '../../services/event.service';
import {Background} from '../../utils/background';
import {EventCategories} from '../../dtos/event-categories';

@Component({
  selector: 'app-top-ten-events',
  templateUrl: './top-ten-events.component.html',
  styleUrls: ['./top-ten-events.component.css']
})
export class TopTenEventsComponent implements OnInit {
  eventCategories: EventCategories[];
  chosenCategory: string;
  error;
  currentMonth: string;
  events: SimpleEvent[];
  hasTicketsSold: boolean = false;


  constructor(private eventService: EventService, private background: Background) {
    this.background.defineTopTenBackground();
  }


  async ngOnInit() {
    this.currentMonth = this.getCurrentMonth();
    this.chosenCategory = 'All';
    this.getTop10Events();
    this.getEventCategories();
    this.background.defineTopTenBackground();
  }

  private getCurrentMonth(): string {
    const months: string[] = ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember' ];
    const now = new Date();
    return months[now.getMonth()];
  }


  /**
   * Tries to load the top 10 events from database
   */
  public getTop10Events() {
    this.events = null;
    if (this.chosenCategory === 'All') {
      console.log('All');
      this.getTop10All();
    } else {
      console.log(this.chosenCategory);
      this.getTop10ByCategory(this.chosenCategory);
    }
  }

  private getTop10All() {
    this.eventService.getTop10Events().subscribe(
      (events: SimpleEvent[]) => {
        this.events = events;
        this.hasTicketsSold = this.events[0].totalTicketsSold > 0;
      },
      error => {
        this.events = null;
        this.error = error.error;
      }
    );
  }

  private getTop10ByCategory(category: string) {
    this.eventService.getTop10EventsByCategory(category).subscribe(
      (events: SimpleEvent[]) => {
        this.events = events;
        this.hasTicketsSold = this.events[0].totalTicketsSold > 0;
      },
      error => {
        this.events = null;
        this.error = error.error;
      }
    );
  }

  private getEventCategories() {
    this.eventService.getAllEventCategories().subscribe((next) => {
      this.eventCategories = next;
    }, (error) => {
      this.error = error.error;
    });
  }
}
