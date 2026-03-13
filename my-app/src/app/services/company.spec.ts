import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { CompanyService } from './company';

describe('CompanyService', () => {
  let service: CompanyService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    
    globalThis.alert = () => {};

    TestBed.configureTestingModule({
      providers: [
        CompanyService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(CompanyService);
    httpMock = TestBed.inject(HttpTestingController);

    httpMock.expectOne('http://localhost:8080/companies').flush([]);
    httpMock
      .match(req =>
        req.url === 'http://localhost:8080/employees' ||
        req.url === 'http://localhost:8080/devices'
      )
      .forEach(req => req.flush([]));
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get companies', () => {
    const mockCompanies = [
      { id: 1, name: 'Company A' },
      { id: 2, name: 'Company B' }
    ];

    service.getCompanies();
    
    const req = httpMock.expectOne('http://localhost:8080/companies');
    expect(req.request.method).toBe('GET');
    
    req.flush(mockCompanies);

    expect(service.allCompanies()[0].name).toBe('Company A');
    expect(service.allCompanies().length).toBe(2);
  });

  it('should create a company', () => {
    const newCompany = { name: 'Company C', address: '123 Main St' };
    
    service.addCompany(newCompany);
    
    const req = httpMock.expectOne('http://localhost:8080/companies');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newCompany);

    req.flush({ id: 3, ...newCompany });
    
    expect(service.allCompanies().some(c => c.name === 'Company C')).toBe(true);
  });

  it('should delete a company', () => {
    const companyId = '1';

    service.deleteCompanyById(companyId);

    const req = httpMock.expectOne(`http://localhost:8080/companies/${companyId}`);
    expect(req.request.method).toBe('DELETE');

    req.flush({});

    httpMock.expectOne('http://localhost:8080/devices').flush([]);
  });
});
