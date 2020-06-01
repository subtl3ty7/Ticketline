import {EventLocation} from './event-location';
import {SimpleEvent} from './simple-event';

export class Show {
  public id: number;
  public eventCode: string;
  public startsAt: Date;
  public endsAt: Date;
  public ticketsSold: number;
  public ticketsAvailable: number;
  public eventLocationCopy: EventLocation;
  public eventLocationOriginalId: number;
  public event: SimpleEvent;
}
