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


  constructor() {
    // define background as gradient from blue to purple
    // document.body.style.background = '-moz-linear-gradient(left, rgba(62,104,201,1) 0%, rgba(219,54,164,1) 100%)';
    // document.body.style.background = '-moz-linear-gradient(left, rgba(26,93,161,1) 0%, rgba(193,130,204,1) 100%)';
    // document.body.style.background = '-moz-linear-gradient(left, rgba(30,133,235,1) 0%, rgba(255,163,214,1) 100%)';
    // document.body.style.background = '-moz-linear-gradient(left, rgba(43,118,184,1) 0%, rgba(153,84,152,1) 100%)';
    document.body.style.background = '-moz-linear-gradient(-30deg, rgba(43,118,184,1) 0%, rgba(200,100,160,1) 100%)';
    document.body.style.backgroundPosition = 'center';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundSize = 'cover';
    // document.body.style.backgroundAttachment = 'fixed';
    this.currentMonth = this.getCurrentMonth();
  }


  ngOnInit(): void {
  }

  getCurrentMonth(): string {
    const months: string[] = ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember' ];
    const now = new Date();
    return months[now.getMonth()];
  }
}
