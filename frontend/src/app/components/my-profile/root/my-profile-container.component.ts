import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-my-profile-container',
  templateUrl: './my-profile-container.component.html',
  styleUrls: ['./my-profile-container.component.css']
})
export class MyProfileContainerComponent implements OnInit {

  constructor(private background: Background) {
    this.background.defineBackground();
  }

  ngOnInit(): void {
  }

}
