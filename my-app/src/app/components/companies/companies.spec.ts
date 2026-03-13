import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { CompaniesComponent } from './companies';
import { CompanyService } from '../../services/company';

describe('CompaniesComponent', () => {
  let component: CompaniesComponent;
  let fixture: ComponentFixture<CompaniesComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompaniesComponent],
      providers: [
        provideHttpClientTesting(),
        CompanyService,
      ],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
    httpMock
      .match(req =>
        req.url === 'http://localhost:8080/companies' ||
        req.url === 'http://localhost:8080/employees' ||
        req.url === 'http://localhost:8080/devices'
      )
      .forEach(req => req.flush([]));

    try {
      fixture = TestBed.createComponent(CompaniesComponent);
      component = fixture.componentInstance;
      await fixture.whenStable();
    } catch (error) {
      console.error('CompaniesComponent creation error:', error);
      throw error;
    }
  });

  it('should create', () => {
    if (!component) {
      throw new Error('CompaniesComponent was not instantiated (component is falsy).');
    }
    expect(component).toBeTruthy();
  });
});
