import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { EmployeesComponent } from './employees';
import { EmployeeService } from '../../services/employee';
import { CompanyService } from '../../services/company';

describe('EmployeesComponent', () => {
  let component: EmployeesComponent;
  let fixture: ComponentFixture<EmployeesComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeesComponent],
      providers: [
        provideHttpClientTesting(),
        EmployeeService,
        CompanyService,
      ],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
    httpMock
      .match(req =>
        req.url === 'http://localhost:8080/employees' ||
        req.url === 'http://localhost:8080/companies' ||
        req.url === 'http://localhost:8080/devices'
      )
      .forEach(req => req.flush([]));

    fixture = TestBed.createComponent(EmployeesComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
