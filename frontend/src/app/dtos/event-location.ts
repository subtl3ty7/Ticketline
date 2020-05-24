import {Section} from './section';
import {Show} from './show';

export class EventLocation {
  public id: number;
  public parentId: string;
  public name: string;
  private eventLocationDescription: string;
  public street: string;
  public plz: string;
  public city: string;
  public country: string;
  public sections: Section[];
  public shows: Show[];
  private capacity: number;
}
