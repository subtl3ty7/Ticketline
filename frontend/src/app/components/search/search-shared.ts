import {Injectable} from '@angular/core';

export enum SearchEntity {
  ARTIST,
  EVENT,
  LOCATION,
  SHOW
}

@Injectable({
  providedIn: 'root'
})
export class SearchShared {
  public searchTerm: string = '';
  public searchEntity: SearchEntity;
}
