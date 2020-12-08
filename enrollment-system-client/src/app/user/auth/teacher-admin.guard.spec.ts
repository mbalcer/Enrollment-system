import {TestBed} from '@angular/core/testing';

import {TeacherAdminGuard} from './teacher-admin.guard';

describe('TeacherAdminGuard', () => {
  let guard: TeacherAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(TeacherAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
