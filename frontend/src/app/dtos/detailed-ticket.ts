import {Seat} from './seat';

export class DetailedTicket {
  public id: number;
  public ticketCode: string;
  public isPurchased: boolean;
  public isReserved: boolean;
  public purchaseDate: string;
  public seat: Seat;
  public price: number;
  public userCode: string;
  public showId: number;
}
