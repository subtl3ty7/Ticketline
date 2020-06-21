import {SimpleTicket} from './simple-ticket';

export class Invoice {
  public id: number;
  public userCode: string;
  public generatedAt: string;
  public invoiceNumber: string;
  public payment_method: string;
  public invoice_type: string;
  public invoice_category: string;
  public merchandise_code: string;
  public tickets: SimpleTicket[];
}
