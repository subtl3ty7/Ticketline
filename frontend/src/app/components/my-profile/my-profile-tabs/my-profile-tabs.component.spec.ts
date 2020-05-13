import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyProfileTabsComponent } from './my-profile-tabs.component';

describe('MyProfileTabsComponent', () => {
  let component: MyProfileTabsComponent;
  let fixture: ComponentFixture<MyProfileTabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyProfileTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyProfileTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
