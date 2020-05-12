import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyInfoTabComponent } from './my-info-tab.component';

describe('MyInfoTabComponent', () => {
  let component: MyInfoTabComponent;
  let fixture: ComponentFixture<MyInfoTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyInfoTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyInfoTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
