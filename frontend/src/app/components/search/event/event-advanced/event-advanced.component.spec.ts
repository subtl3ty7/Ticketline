import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventAdvancedComponent } from './event-advanced.component';

describe('EventAdvancedComponent', () => {
  let component: EventAdvancedComponent;
  let fixture: ComponentFixture<EventAdvancedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventAdvancedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventAdvancedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
