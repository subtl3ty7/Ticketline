import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentMethodOverviewComponent } from './payment-method-overview.component';

describe('PaymentMethodOverviewComponent', () => {
  let component: PaymentMethodOverviewComponent;
  let fixture: ComponentFixture<PaymentMethodOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentMethodOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentMethodOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
