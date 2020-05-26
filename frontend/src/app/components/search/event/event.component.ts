import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../dtos/artist';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute} from '@angular/router';
import {SearchShared} from '../search-shared';
import {number} from '@amcharts/amcharts4/core';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  name: string = '';
  artistId: string;
  constructor(public authService: AuthService, private artistService: ArtistService, private activatedRoute: ActivatedRoute, private searchShared: SearchShared) {

  }

  ngOnInit(): void {
    console.log('event search');
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
    this.name = sessionStorage.getItem('searchTerm');
    console.log('event name: ' + this.name);
    this.searchShared.getEventsByName(this.name);
  }

}
