import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsDetailsContainerComponent } from './news-details-container.component';

describe('NewsDetailsContainerComponent', () => {
  let component: NewsDetailsContainerComponent;
  let fixture: ComponentFixture<NewsDetailsContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewsDetailsContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsDetailsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
