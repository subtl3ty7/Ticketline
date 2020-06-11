import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyInvoicesTabComponent } from './my-invoices-tab.component';

describe('MyInvoicesTabComponent', () => {
  let component: MyInvoicesTabComponent;
  let fixture: ComponentFixture<MyInvoicesTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyInvoicesTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyInvoicesTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
