import {Show} from './show';

export class SimpleEvent {
  public eventCode: string;
  public startsAt: string;  // will be LocalDateTime in backend
  public endsAt: string; // will be LocalDateTime in backend
  public name: string;
  public description: string;
  public startPrice: number;
  public totalTicketsSold: number;
  public photo: number;

}
