import {TestBed} from '@angular/core/testing';

import {FieldOfStudyService} from './field-of-study.service';

describe('FieldOfStudyService', () => {
  let service: FieldOfStudyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FieldOfStudyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
