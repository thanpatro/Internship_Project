import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { DevicesComponent } from './devices';
import { DeviceService } from '../../services/device';
import { EmployeeService } from '../../services/employee';

describe('DevicesComponent', () => {
  let component: DevicesComponent;
  let fixture: ComponentFixture<DevicesComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DevicesComponent],
      providers: [
        provideHttpClientTesting(),
        DeviceService,
        EmployeeService,
      ],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
    httpMock
      .match(req =>
        req.url === 'http://localhost:8080/devices' ||
        req.url === 'http://localhost:8080/employees'
      )
      .forEach(req => req.flush([]));

    try {
      fixture = TestBed.createComponent(DevicesComponent);
      component = fixture.componentInstance;
      await fixture.whenStable();
    } catch (err) {
      console.error('DevicesComponent creation error:', err);
      throw err;
    }
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
