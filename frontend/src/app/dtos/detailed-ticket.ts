import {Seat} from './seat';
import {Show} from './show';

export class DetailedTicket {
  public id: number;
  public ticketCode: string;
  public purchased: boolean;
  public reserved: boolean;
  public cancelled: boolean;
  public purchaseDate: string;
  public seat: Seat;
  public price: number;
  public userCode: string;
  public show: Show;
  public event: Event;
}
