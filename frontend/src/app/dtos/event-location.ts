import {Section} from './section';

export class EventLocation {
  public id: number;
  public parentId: number;
  public name: string;
  public eventLocationDescription;
  public street: string;
  public plz: string;
  public city: string;
  public country: string;
  public sections: Section[];
  public capacity: number;
}
