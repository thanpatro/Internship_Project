import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { Sidebar } from './sidebar';
import { CompanyService } from '../../services/company';
import { EmployeeService } from '../../services/employee';
import { DeviceService } from '../../services/device';

describe('Sidebar', () => {
  let component: Sidebar;
  let fixture: ComponentFixture<Sidebar>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Sidebar],
      providers: [
        provideHttpClientTesting(),
        CompanyService,
        EmployeeService,
        DeviceService,
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
      fixture = TestBed.createComponent(Sidebar);
      component = fixture.componentInstance;
      await fixture.whenStable();
    } catch (err) {
      console.error('Sidebar creation error:', err);
      throw err;
    }
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
