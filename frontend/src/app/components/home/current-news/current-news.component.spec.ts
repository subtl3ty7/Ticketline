import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentNewsComponent } from './current-news.component';

describe('CurrentNewsComponent', () => {
  let component: CurrentNewsComponent;
  let fixture: ComponentFixture<CurrentNewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentNewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
