import {Component, Input, OnInit} from '@angular/core';
import {DetailedEvent} from '../../../../../../../dtos/detailed-event';
import {MatDialog} from '@angular/material/dialog';
import {CreateShowsComponent} from './create-shows/create-shows.component';
import {CreateArtistsComponent} from './create-artists/create-artists.component';
import {EventCategories} from '../../../../../../../dtos/event-categories';
import {EventService} from '../../../../../../../services/event.service';
import {EventTypes} from '../../../../../../../dtos/event-types';


@Component({
  selector: 'app-create-event-form',
  templateUrl: './create-event-form.component.html',
  styleUrls: ['./create-event-form.component.css']
})
export class CreateEventFormComponent implements OnInit {
  @Input() event: DetailedEvent;
  photo;
  error;
  eventCategories: EventCategories[];
  eventTypes: EventTypes[];
  constructor(public dialog: MatDialog,
              private eventService: EventService) {
  }

  ngOnInit(): void {
    this.event.artists = [];
    this.event.shows = [];
    this.event.prices = [];
    this.event.totalTicketsSold = 0;
    this.getEventCategories();
    this.getEventTypes();
  }

  inputChange(fileInputEvent: any) {
    this.photo = fileInputEvent.target.files[0];
    const rd = new FileReader();
    rd.onload = this.handleFile.bind(this);
    rd.readAsDataURL(this.photo);
  }

  handleFile(event) {
    this.event.photo = event.target.result;
  }

  openShowDialog() {
    const dialogRef = this.dialog.open(CreateShowsComponent,
      {width: '100%', data: this.event.shows});
    dialogRef.afterClosed().subscribe(() => {
      console.log(this.event.shows);
    });
  }

  openArtistsDialog() {
    const dialogRef = this.dialog.open(CreateArtistsComponent,
      {width: '100%', data: this.event.artists});
    dialogRef.afterClosed().subscribe(() => {
    });
  }

  getEventCategories() {
    this.eventService.getAllEventCategories().subscribe((next) => {
      this.eventCategories = next;
    }, (error) => {
      this.error = error.error;
    });
  }
  getEventTypes() {
    this.eventService.getAllEventTypes().subscribe((next) => {
      this.eventTypes = next;
    }, (error) => {
      this.error = error.error;
    });
  }
}
