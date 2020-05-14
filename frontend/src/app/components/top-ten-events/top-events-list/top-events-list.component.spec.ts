import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopEventsListComponent } from './top-events-list.component';

describe('TopTenEventsListComponent', () => {
  let component: TopEventsListComponent;
  let fixture: ComponentFixture<TopEventsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopEventsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopEventsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
