import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { DeviceService } from './device';

describe('DeviceService', () => {
  let service: DeviceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    
    globalThis.alert = () => {};

    TestBed.configureTestingModule({
      providers: [
        DeviceService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(DeviceService);
    httpMock = TestBed.inject(HttpTestingController);

    httpMock.expectOne('http://localhost:8080/devices').flush([]);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get devices', () => {
    const mockDevices = [
      { serialNumber: 1, name: 'Device A', type: 'Type A', employeeId: '1' },
      { serialNumber: 2, name: 'Device B', type: 'Type B', employeeId: '2' }
    ];

    service.getDevices();
    const req = httpMock.expectOne('http://localhost:8080/devices');

    req.flush(mockDevices);

    expect(service.allDevices().length).toBe(2);
    expect(service.allDevices()[0].name).toBe('Device A');
    expect(service.allDevices()[0].type).toBe('Type A');
  });

  it('should create a device', () => {
    const newDevice = { name: 'Device C', type: 'Type C', employeeId: '1' };
    
    service.addDevice(newDevice);
    const req = httpMock.expectOne('http://localhost:8080/devices');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newDevice);

    req.flush({ id: 3, ...newDevice });
    expect(service.allDevices().length).toBe(1);
    expect(service.allDevices()[0].name).toBe('Device C');
  });

  it('should delete a device', () => {
    const deviceId = '1';
    service.deleteDeviceById(deviceId);
    const req = httpMock.expectOne(`http://localhost:8080/devices/${deviceId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});

