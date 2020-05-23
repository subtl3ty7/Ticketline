import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationAdvancedComponent } from './location-advanced.component';

describe('LocationAdvancedComponent', () => {
  let component: LocationAdvancedComponent;
  let fixture: ComponentFixture<LocationAdvancedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationAdvancedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationAdvancedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
