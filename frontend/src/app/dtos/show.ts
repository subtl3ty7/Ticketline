import {EventLocation} from './event-location';
import {SimpleEvent} from './simple-event';

enum EventTypeEnum {
}

enum EventCategoryEnum {
}

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
  public eventType: EventTypeEnum;
  public eventCategory: EventCategoryEnum;
  public duration: number;
  public price: number;
  public eventName: string;
}
