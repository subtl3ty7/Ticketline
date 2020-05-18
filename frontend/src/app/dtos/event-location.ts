import {Section} from './section';

export class EventLocation {
  public id: number;
  public showId: string;
  public name: string;
  public street: string;
  public plz: string;
  public city: string;
  public country: string;
  public sections: Section[];
}
