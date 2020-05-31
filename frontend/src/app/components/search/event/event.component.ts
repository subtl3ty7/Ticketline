import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../dtos/artist';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute} from '@angular/router';
import {SearchShared} from '../search-shared';
import {number} from '@amcharts/amcharts4/core';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  name: string = '';
  artistId: string;
  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('event search');
    this.name = sessionStorage.getItem('searchTerm');
    if (sessionStorage.getItem('isAdvancedSearchActive') === String(true)) {
      const type = sessionStorage.getItem('eventType');
      const category = sessionStorage.getItem('eventCategory');
      const startsAt = sessionStorage.getItem('eventStartsAt');
      const endsAt = sessionStorage.getItem('eventEndsAt');
      const duration = sessionStorage.getItem('eventDuration');
      console.log('event name: ' + this.name + ', event type: ' + type + ' event category: ' + category + 'starts at: ' + startsAt + ', event ends at: ' + endsAt + ', event duration: ' + duration);
      this.searchShared.getEventsBy(name, type, category, startsAt, endsAt, duration);
    } else {
      console.log('event name: ' + this.name);
      this.searchShared.getEventsByName(this.name);
    }
  }

}
