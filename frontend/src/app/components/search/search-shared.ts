import {Injectable} from '@angular/core';
import {Artist} from '../../dtos/artist';
import {AuthService} from '../../services/auth.service';
import {ArtistService} from '../../services/artist.service';
import {ActivatedRoute} from '@angular/router';

export enum SearchEntity {
  ARTIST = 'Artist',
  EVENT = 'Event',
  LOCATION = 'Location',
  SHOW = 'Show'
}

@Injectable({
  providedIn: 'root',
})
export class SearchShared {
  public searchTerm: string = '';
  public searchEntity: SearchEntity;
  public entities: object[];
  
  constructor(public authService: AuthService, private artistService: ArtistService) { }

  public getArtistsByFirstAndLastName(firstName: string, lastName: string) {
    console.log('loading all artists with first name containing "' + firstName + '" and last name containing "' + lastName + '"');
    this.artistService.getArtistsByFirstAndLastName(firstName, lastName).subscribe(
      (artists: Artist[]) => {
        this.entities = artists;
      },
      error => {
        this.entities = null;
      }
    );
  }
}
