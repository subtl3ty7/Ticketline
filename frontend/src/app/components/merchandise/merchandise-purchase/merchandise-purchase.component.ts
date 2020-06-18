import { Component, OnInit } from '@angular/core';
import {Merchandise} from '../../../dtos/merchandise';
import {MerchandiseService} from '../../../services/merchandise.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-merchandise-purchase',
  templateUrl: './merchandise-purchase.component.html',
  styleUrls: ['./merchandise-purchase.component.css']
})
export class MerchandisePurchaseComponent implements OnInit {

  error;
  merchandiseProduct: Merchandise;
  quantity: number;


  constructor(
    private merchandiseService: MerchandiseService,
    private router: Router,
    private route: ActivatedRoute,
    private background: Background) {
    this.background.defineBackground();
  }


  ngOnInit(): void {
    this.loadMerchandiseProduct();
  }


  public loadMerchandiseProduct(): void {
    const merchandiseProductCode = this.route.snapshot.paramMap.get('merchandiseProductCode');
    this.merchandiseService.getMerchandiseProductByProductCode(merchandiseProductCode).subscribe(
      (merchandiseProduct: Merchandise) => {
        this.merchandiseProduct = merchandiseProduct;
      },
      (error) => {
        this.error = error;
      }
    );
  }

  public changePrice() {
    console.log('Changing price');
    console.log(this.quantity);
    this.merchandiseProduct.price = this.merchandiseProduct.price * this.quantity;
  }



}
