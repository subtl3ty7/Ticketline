import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsDetailsInnerComponent } from './news-details-inner.component';

describe('NewsDetailsInnerComponent', () => {
  let component: NewsDetailsInnerComponent;
  let fixture: ComponentFixture<NewsDetailsInnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewsDetailsInnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsDetailsInnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
