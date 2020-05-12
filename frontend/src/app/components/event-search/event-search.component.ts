import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrls: ['./event-search.component.css']
})
export class EventSearchComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
  }

}
