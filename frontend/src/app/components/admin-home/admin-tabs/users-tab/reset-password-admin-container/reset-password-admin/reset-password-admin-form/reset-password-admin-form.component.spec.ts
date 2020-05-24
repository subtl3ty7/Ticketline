import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordAdminFormComponent } from './reset-password-admin-form.component';

describe('ResetPasswordAdminFormComponent', () => {
  let component: ResetPasswordAdminFormComponent;
  let fixture: ComponentFixture<ResetPasswordAdminFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResetPasswordAdminFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordAdminFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
