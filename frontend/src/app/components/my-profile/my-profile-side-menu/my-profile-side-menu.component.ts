import { Component, OnInit } from '@angular/core';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-my-profile-side-menu',
  templateUrl: './my-profile-side-menu.component.html',
  styleUrls: ['./my-profile-side-menu.component.css']
})
export class MyProfileSideMenuComponent implements OnInit {

  constructor(private globals: Globals) { }

  ngOnInit(): void {
  }

}
