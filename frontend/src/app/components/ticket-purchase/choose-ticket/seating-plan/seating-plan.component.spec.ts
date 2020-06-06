import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeatingPlanComponent } from './seating-plan.component';

describe('SeatingPlanComponent', () => {
  let component: SeatingPlanComponent;
  let fixture: ComponentFixture<SeatingPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeatingPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeatingPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
