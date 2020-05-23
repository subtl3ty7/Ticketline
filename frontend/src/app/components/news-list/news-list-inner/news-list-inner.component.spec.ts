import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsListInnerComponent } from './news-list-inner.component';

describe('NewsListInnerComponent', () => {
  let component: NewsListInnerComponent;
  let fixture: ComponentFixture<NewsListInnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewsListInnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsListInnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
