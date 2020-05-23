import {Seat} from './seat';

export class Section {
  public id: number;
  private eventLocationId: number;
  public sectionName: string;
  private description: string;
  public seats: Seat[];
}
