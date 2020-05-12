import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyProfileSideMenuComponent } from './my-profile-side-menu.component';

describe('MyProfileSideMenuComponent', () => {
  let component: MyProfileSideMenuComponent;
  let fixture: ComponentFixture<MyProfileSideMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyProfileSideMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyProfileSideMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
