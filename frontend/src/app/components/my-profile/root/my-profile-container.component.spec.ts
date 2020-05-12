import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyProfileContainerComponent } from './my-profile-container.component';

describe('MyProfileContainerComponent', () => {
  let component: MyProfileContainerComponent;
  let fixture: ComponentFixture<MyProfileContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyProfileContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyProfileContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
