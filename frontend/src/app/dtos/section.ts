import {Seat} from './seat';

export class Section {
  private id: number;
  private eventLocationId: number;
  private name: string;
  private description: string;
  private seats: Seat[];
}
