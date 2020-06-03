import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {Background} from '../../../utils/background';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../search-shared';

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css']
})
export class ShowComponent implements OnInit {

  name: string = '';

  constructor(private background: Background,
              public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private router: Router
              ) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('show search');
    this.name = sessionStorage.getItem('searchTerm');
    if (sessionStorage.getItem('isAdvancedSearchActive') === String(true)) {
      const type = sessionStorage.getItem('eventType');
      const category = sessionStorage.getItem('eventCategory');
      const duration = sessionStorage.getItem('eventDuration');
      const startPrice = sessionStorage.getItem('eventStartPrice');
      const showStartsAt = sessionStorage.getItem('eventShowStartsAt');
      const showEndsAt = sessionStorage.getItem('eventShowEndsAt');
      console.log('show name: ' + this.name + ', show type: ' + type + ' show category: ' + category + 'starts at: ' + showStartsAt + ', event ends at: ' + showEndsAt + ', event duration: ' + duration + ', event start price: ' + startPrice);
      this.searchShared.getShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice);
    }
  }

  openPurchase(show) {
    if (show) {
      sessionStorage.setItem('show', JSON.stringify(show));
      this.router.navigate(['ticket-purchase']);
    }
  }
}
