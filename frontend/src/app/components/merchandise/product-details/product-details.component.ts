import { Component, OnInit } from '@angular/core';
import {Merchandise} from '../../../dtos/merchandise';
import {MerchandiseService} from '../../../services/merchandise.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Background} from '../../../utils/background';
import {DetailedEvent} from '../../../dtos/detailed-event';
import {User} from '../../../dtos/user';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  error;
  merchandiseProduct: Merchandise;
  user: User;

  constructor(
    private merchandiseService: MerchandiseService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private background: Background) {
      this.background.defineBackground();
  }

  ngOnInit(): void {
    this.loadMerchandiseProduct();
    this.loadUserByUserCode();
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

  public loadUserByUserCode(): void {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        this.user = user;
      },
      (error) => {
        this.error = error;
      }
    );
  }
  openPurchase(product: Merchandise) {
    if (product) {
      this.router.navigate(['merchandise-purchase/' + product.merchandiseProductCode]);
    }
  }

  purchaseWithPremium(product: Merchandise) {
    this.merchandiseService.purchaseWithPremium(product, this.user.userCode).subscribe(
    );
  }



}
