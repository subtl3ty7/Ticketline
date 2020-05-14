import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventDetailsContainerComponent } from './event-details-container.component';

describe('EventDetailsContainerComponent', () => {
  let component: EventDetailsContainerComponent;
  let fixture: ComponentFixture<EventDetailsContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventDetailsContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventDetailsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
