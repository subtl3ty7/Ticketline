import {Section} from './section';

export class EventLocation {
  private id: number;
  private showId: string;
  private eventLocationName: string;
  private street: string;
  private plz: string;
  private city: string;
  private country: string;
  private sections: Section[];
}
