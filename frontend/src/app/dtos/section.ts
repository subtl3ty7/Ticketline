import {Seat} from './seat';

export class Section {
  public id: number;
  private eventLocationId: number;
  public name: string;
  private description: string;
  public seats: Seat[];
  public price: number;
}
