import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyTicketsTabComponent } from './my-tickets-tab.component';

describe('MyTicketsTabComponent', () => {
  let component: MyTicketsTabComponent;
  let fixture: ComponentFixture<MyTicketsTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyTicketsTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyTicketsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
