import { Component, OnInit } from '@angular/core';
import {Merchandise} from '../../../dtos/merchandise';
import {MerchandiseService} from '../../../services/merchandise.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  error;
  merchandiseProduct: Merchandise;

  constructor(
    private merchandiseService: MerchandiseService,
    private router: Router,
    private route: ActivatedRoute,
    private background: Background) {
      this.background.defineBackground();
  }

  ngOnInit(): void {
  }



}
