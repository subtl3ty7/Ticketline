import {EventLocation} from './event-location';

export class Show {
  private id: number;
  private eventCode: string;
  private startsAt: string;
  private endsAt: string;
  private ticketsSold: number;
  private ticketsAvailable: number;
  private eventLocation: EventLocation[];
}
