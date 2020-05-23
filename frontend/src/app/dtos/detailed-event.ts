import {Show} from './show';

export class DetailedEvent {
  public eventCode: string;
  public startsAt: Date;  // will be LocalDateTime in backend
  public endsAt: Date; // will be LocalDateTime in backend
  public name: string;
  public description: string;
  public startPrice: number;
  public photo: number;
  public shows: Show[];
  public artists: string[];
  public type: string;
  public category: string;
  public prices: number[];
  public totalTicketsSold: number;

}
