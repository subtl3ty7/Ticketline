import { Component, OnInit } from '@angular/core';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-admin-side-menu',
  templateUrl: './admin-side-menu.component.html',
  styleUrls: ['./admin-side-menu.component.css']
})
export class AdminSideMenuComponent implements OnInit {

  constructor(private globals: Globals) { }

  ngOnInit(): void {
  }

}
