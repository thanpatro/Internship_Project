import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { EmployeeService } from './employee';

describe('EmployeeService', () => {
  let service: EmployeeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    
    globalThis.alert = () => {};

    TestBed.configureTestingModule({
      providers: [
        EmployeeService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(EmployeeService);
    httpMock = TestBed.inject(HttpTestingController);

    
    httpMock.expectOne('http://localhost:8080/employees').flush([]);
    httpMock.match('http://localhost:8080/devices').forEach(req => req.flush([]));
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get employees', () => {
    const mockEmployees = [
      { id: 1, name: 'Employee A' , email : 'employee.a@example.com', companyId: '1' },
      { id: 2, name: 'Employee B' , email : 'employee.b@example.com', companyId: '1' }
    ];

    service.getEmployees();
    const req = httpMock.expectOne('http://localhost:8080/employees');

    req.flush(mockEmployees);

    expect(service.allEmployees().length).toBe(2);
    expect(service.allEmployees()[0].name).toBe('Employee A');
    expect(service.allEmployees()[0].email).toBe('employee.a@example.com');
  });

  it('should create an employee', () => {
    const newEmployee = { name: 'Employee C' , email : 'employee.c@example.com', companyId: '1'};
    
    service.addEmployee(newEmployee);
    const req = httpMock.expectOne('http://localhost:8080/employees');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newEmployee);

    req.flush({ id: 3, ...newEmployee });
    expect(service.allEmployees().length).toBe(1);
    expect(service.allEmployees()[0].name).toBe('Employee C');
  });

  it('should delete an employee', () => {
    const employeeId = '1';
    service.deleteEmployeeById(employeeId);
    const req = httpMock.expectOne(`http://localhost:8080/employees/${employeeId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});

