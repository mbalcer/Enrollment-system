import {TestBed} from '@angular/core/testing';

import {SubjectGroupService} from './subject-group.service';

describe('SubjectGroupService', () => {
  let service: SubjectGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectGroupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
