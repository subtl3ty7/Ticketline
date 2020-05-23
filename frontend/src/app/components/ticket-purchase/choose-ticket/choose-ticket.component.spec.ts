import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseTicketComponent } from './choose-ticket.component';

describe('ChooseTicketComponent', () => {
  let component: ChooseTicketComponent;
  let fixture: ComponentFixture<ChooseTicketComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseTicketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
