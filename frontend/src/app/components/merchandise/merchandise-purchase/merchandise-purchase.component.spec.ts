import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchandisePurchaseComponent } from './merchandise-purchase.component';

describe('MerchandisePurchaseComponent', () => {
  let component: MerchandisePurchaseComponent;
  let fixture: ComponentFixture<MerchandisePurchaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MerchandisePurchaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MerchandisePurchaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
