import {EventLocation} from './event-location';

export class Show {
  public id: number;
  public eventCode: string;
  public startsAt: Date;
  public endsAt: Date;
  public ticketsSold: number;
  public ticketsAvailable: number;
  public eventLocationCopy: EventLocation;
}
