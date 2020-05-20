import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {Artist} from '../../../dtos/artist';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-artist',
  templateUrl: './artist.component.html',
  styleUrls: ['./artist.component.css']
})
export class ArtistComponent implements OnInit {

  firstName: string = '';
  lastName: string = '';
  artists: Artist[];

  constructor(public authService: AuthService, private artistService: ArtistService, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.firstName = params['firstName'];
      this.lastName = params['lastName'];
      console.log('first name: ' + this.firstName + ', last name: ' + this.lastName);
      this.getArtistsByFirstAndLastName(this.firstName, this.lastName);
    });
  }

  ngOnInit(): void {
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
  }

  public getArtistsByFirstAndLastName(firstName: string, lastName: string) {
    this.artistService.getArtistsByFirstAndLastName(firstName, lastName).subscribe(
      (artists: Artist[]) => {
        this.artists = artists;
      },
      error => {
        this.artists = null;
      }
    );
  }
}
