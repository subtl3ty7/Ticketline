import { Injectable } from '@angular/core';
import {SimpleTicket} from '../dtos/simple-ticket';
import {DetailedTicket} from '../dtos/detailed-ticket';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private ticketBaseUri: string = this.globals.backendUri + '/tickets';

  constructor( private httpClient: HttpClient,
               private globals: Globals,
               private router: Router) { }



}
