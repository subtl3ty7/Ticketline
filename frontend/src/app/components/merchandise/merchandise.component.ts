import { Component, OnInit } from '@angular/core';
import {MerchandiseService} from '../../services/merchandise.service';
import {Merchandise} from '../../dtos/merchandise';

@Component({
  selector: 'app-merchandise',
  templateUrl: './merchandise.component.html',
  styleUrls: ['./merchandise.component.css']
})
export class MerchandiseComponent implements OnInit {

  error;
  merchandise: Merchandise[];

  constructor(private merchandiseService: MerchandiseService) { }

  ngOnInit(): void {
    this.getAllMerchandiseProducts();
  }

  public getAllMerchandiseProducts(): void {
    this.merchandiseService.getAllMerchandiseProducts().subscribe(
      (merchandise: Merchandise[]) => {
        this.merchandise = merchandise;
      },
      (error1) => {
        this.error = error1;
      }
    );
  }

}
