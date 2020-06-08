import {SimpleTicket} from './simple-ticket';

export class Invoice {
  public id: number;
  public userCode: string;
  public generatedAt: string;
  public invoice_number: string;
  public payment_method: string;
  public invoice_type: string;
  public tickets: SimpleTicket[];
}
