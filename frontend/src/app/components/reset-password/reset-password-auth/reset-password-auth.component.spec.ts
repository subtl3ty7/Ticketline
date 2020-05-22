import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordAuthComponent } from './reset-password-auth.component';

describe('ResetPasswordAuthComponent', () => {
  let component: ResetPasswordAuthComponent;
  let fixture: ComponentFixture<ResetPasswordAuthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResetPasswordAuthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordAuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
