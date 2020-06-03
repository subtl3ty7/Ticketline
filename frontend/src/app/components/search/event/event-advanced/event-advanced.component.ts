import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ArtistService} from '../../../../services/artist.service';
import {SearchShared} from '../../search-shared';

@Component({
  selector: 'app-event-advanced',
  templateUrl: './event-advanced.component.html',
  styleUrls: ['./event-advanced.component.css']
})
export class EventAdvancedComponent implements OnInit {

  advancedEventSearchForm: FormGroup;
  submitted: boolean = false;

  // Event + Show parameters
  name: string = '';
  type: string = '';
  category: string = '';
  startsAt: string = '';
  endsAt: string = '';
  duration: string = '';

  // Show-exclusive parameters
  startPrice: string = '';
  showStartsAt: string = '';
  showEndsAt: string = '';

  constructor(private formBuilder: FormBuilder, public authService: AuthService, public router: Router, private artistService: ArtistService, private activatedRoute: ActivatedRoute, private searchShared: SearchShared) {
    this.advancedEventSearchForm = new FormGroup({
      name: new FormControl(),
      type: new FormControl(),
      category: new FormControl(),
      startsAt: new FormControl(),
      endsAt: new FormControl(),
      duration: new FormControl(),
      startPrice: new FormControl(),
      showStartsAt: new FormControl(),
      showEndsAt: new FormControl()
    });
  }

  ngOnInit(): void {
    console.log('advanced event search');
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
  }

  advancedSearch() {
    this.submitted = true;
    if (this.advancedEventSearchForm.valid) {
      this.name = this.advancedEventSearchForm.controls.name.value === null ? '' : this.advancedEventSearchForm.controls.name.value;
      this.type = this.advancedEventSearchForm.controls.type.value  === null ? '' : this.advancedEventSearchForm.controls.type.value;
      this.category = this.advancedEventSearchForm.controls.category.value === null ? '' : this.advancedEventSearchForm.controls.category.value;
      this.startsAt = this.advancedEventSearchForm.controls.startsAt.value === null ? '' : this.advancedEventSearchForm.controls.startsAt.value;
      this.endsAt = this.advancedEventSearchForm.controls.endsAt.value === null ? '' : this.advancedEventSearchForm.controls.endsAt.value;
      this.duration = this.advancedEventSearchForm.controls.duration.value === null ? '' : this.advancedEventSearchForm.controls.duration.value;
      this.startPrice = this.advancedEventSearchForm.controls.startPrice.value === null ? '' : this.advancedEventSearchForm.controls.startPrice.value;
      this.showStartsAt = this.advancedEventSearchForm.controls.showStartsAt.value === null ? '' : this.advancedEventSearchForm.controls.showStartsAt.value;
      this.showEndsAt = this.advancedEventSearchForm.controls.showEndsAt.value === null ? '' : this.advancedEventSearchForm.controls.showEndsAt.value;
      console.log('advanced searching with name: ' + this.name + ', type: ' + this.type + ', category: ' + this.category + ', startsAt: ' + this.startsAt + ', endsAt: ' + this.endsAt + ', duration: ' + this.duration + ', startPrice: ' + this.startPrice);
      sessionStorage.setItem('isAdvancedSearchActive', String(true));
      sessionStorage.setItem('searchTerm', this.name);
      sessionStorage.setItem('eventType', this.type);
      sessionStorage.setItem('eventCategory', this.category);
      sessionStorage.setItem('eventStartsAt', this.startsAt);
      sessionStorage.setItem('eventEndsAt', this.endsAt);
      sessionStorage.setItem('eventDuration', this.duration);
      if (this.startPrice !== '' || this.showStartsAt !== '' || this.showEndsAt !== '') {
        sessionStorage.setItem('eventStartPrice', this.startPrice);
        sessionStorage.setItem('eventShowStartsAt', this.showStartsAt);
        sessionStorage.setItem('eventShowEndsAt', this.showEndsAt);
        sessionStorage.setItem('searchEntity', 'Show');
      } else {
        sessionStorage.setItem('searchEntity', 'Event');
      }
      this.router.navigate(['/search']);
    }
  }

  resetForm() {
    this.advancedEventSearchForm.controls.name.setValue('');
    this.advancedEventSearchForm.controls.type.setValue('');
    this.advancedEventSearchForm.controls.category.setValue('');
    this.advancedEventSearchForm.controls.startsAt.setValue('');
    this.advancedEventSearchForm.controls.endsAt.setValue('');
    this.advancedEventSearchForm.controls.duration.setValue('');
    this.advancedEventSearchForm.controls.startPrice.setValue('');
  }
}
