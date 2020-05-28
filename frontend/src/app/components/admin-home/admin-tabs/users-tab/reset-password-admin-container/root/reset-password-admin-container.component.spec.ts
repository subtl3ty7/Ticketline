import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordAdminContainerComponent } from './reset-password-admin-container.component';

describe('ResetPasswordAdminContainerComponent', () => {
  let component: ResetPasswordAdminContainerComponent;
  let fixture: ComponentFixture<ResetPasswordAdminContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResetPasswordAdminContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordAdminContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
