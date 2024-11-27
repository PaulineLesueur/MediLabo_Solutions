import { TestBed } from '@angular/core/testing';

import { DiabeteReportService } from './diabete-report.service';

describe('DiabeteReportService', () => {
  let service: DiabeteReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiabeteReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
