import { Component, OnInit } from '@angular/core';
import {Merchandise} from '../../../dtos/merchandise';
import {MerchandiseService} from '../../../services/merchandise.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Background} from '../../../utils/background';
import {UserService} from '../../../services/user.service';
import {User} from '../../../dtos/user';

@Component({
  selector: 'app-merchandise-purchase',
  templateUrl: './merchandise-purchase.component.html',
  styleUrls: ['./merchandise-purchase.component.css']
})
export class MerchandisePurchaseComponent implements OnInit {

  error;
  merchandiseProduct: Merchandise;
  quantity: number;
  private finalPrice: number;
  private finalPremiumPrice: number;

  user = User;

  constructor(
    private merchandiseService: MerchandiseService,
    private router: Router,
    private route: ActivatedRoute,
    private background: Background,
    private userService: UserService) {
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
    console.log(this.merchandiseProduct.price);
    console.log('Changing price');
    this.merchandiseProduct.price = this.merchandiseProduct.price * this.quantity;
  }

  public changePremiumPrice() {
    console.log('Changing premium price');
    this.merchandiseProduct.premiumPrice = this.merchandiseProduct.premiumPrice * this.quantity;
  }


  /*public completePurchase() {
      this.merchandiseService.purchase(this.merchandiseProduct, null).subscribe(

      );
  }*/



}
