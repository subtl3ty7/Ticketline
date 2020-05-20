import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {SimpleEvent} from '../../dtos/simple-event';
import {EventService} from '../../services/event.service';

@Component({
  selector: 'app-top-ten-events',
  templateUrl: './top-ten-events.component.html',
  styleUrls: ['./top-ten-events.component.css']
})
export class TopTenEventsComponent implements OnInit {
  currentMonth: string;
  events: SimpleEvent[];


  constructor(private eventService: EventService) {
  }


  async ngOnInit() {
    this.currentMonth = this.getCurrentMonth();
    this.getTop10Events();
    this.defineBackground();
  }

  getCurrentMonth(): string {
    const months: string[] = ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember' ];
    const now = new Date();
    return months[now.getMonth()];
  }


  /**
   * Tries to load the top 10 events from database
   */
  public getTop10Events() {
    this.eventService.getTop10Events().subscribe(
      (events: SimpleEvent[]) => {
        this.events = events;
      },
      error => {
        this.events = null;
      }
    );
  }

  private defineBackground() {
    // define background as gradient from blue to purple
    document.body.style.background = 'rgba(43,121,184,1)';
    document.body.style.background = '-moz-linear-gradient(left, rgba(43,121,184,1) 0%, rgba(201,100,161,1) 100%)';
    document.body.style.background = '-webkit-gradient(left top, right top, color-stop(0%, rgba(43,121,184,1)), color-stop(100%, rgba(201,100,161,1)))';
    document.body.style.background = '-webkit-linear-gradient(left, rgba(43,121,184,1) 0%, rgba(201,100,161,1) 100%)';
    document.body.style.background = '-o-linear-gradient(left, rgba(43,121,184,1) 0%, rgba(201,100,161,1) 100%)';
    document.body.style.background = '-ms-linear-gradient(left, rgba(43,121,184,1) 0%, rgba(201,100,161,1) 100%)';
    document.body.style.background = 'linear-gradient(to right, rgba(43,121,184,1) 0%, rgba(201,100,161,1) 100%)';
    document.body.style.filter = 'progid:DXImageTransform.Microsoft.gradient( startColorstr=\'#2b79b8\', endColorstr=\'#c964a1\', GradientType=1 )';
    document.body.style.backgroundPosition = 'center';
    document.body.style.backgroundRepeat = 'repeat';
    document.body.style.backgroundSize = 'cover';
  }

  private delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }
}
