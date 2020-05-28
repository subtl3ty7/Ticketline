import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css']
})
export class ShowComponent implements OnInit {

  constructor(private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
  }

}
