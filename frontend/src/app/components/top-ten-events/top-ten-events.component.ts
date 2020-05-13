import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-top-ten-events',
  templateUrl: './top-ten-events.component.html',
  styleUrls: ['./top-ten-events.component.css']
})
export class TopTenEventsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
