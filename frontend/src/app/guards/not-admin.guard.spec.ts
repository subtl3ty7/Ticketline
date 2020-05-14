import { TestBed } from '@angular/core/testing';

import {NotAdminGuard} from './not-admin.guard';

describe('NotAdminGuard', () => {
  let guard: NotAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(NotAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
