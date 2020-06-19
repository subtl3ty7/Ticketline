import {Show} from './show';
import {Artist} from './artist';

export class DetailedEvent {
  public eventCode: string;
  public startsAt: Date;  // will be LocalDateTime in backend
  public endsAt: Date; // will be LocalDateTime in backend
  public name: string;
  public description: string;
  public startPrice: number;
  public photo: string;
  public shows: Show[];
  public artists: Artist[];
  public type: string;
  public category: string;
  public prices: number[];
  public totalTicketsSold: number;
  public duration: number;
}
