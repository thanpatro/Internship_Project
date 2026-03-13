import { Injectable, signal, inject } from '@angular/core';
import { Device } from '../models/device';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/devices'; 

  private devices = signal<Device[]>([]);

  constructor() {
    this.getDevices();
  }

  getDevices() {
    this.http.get<Device[]>(this.apiUrl).subscribe(devices => {
      this.devices.set(devices);
    });
   }

  get allDevices(){
    return this.devices;
  }

  getDeviceById(serial_number: string):Device | undefined{
    return this.devices().find(d => d.serialNumber === serial_number);
  }

  addDevice(device: Device){
    this.http.post<Device>(this.apiUrl, device).subscribe({
    next: (newDevice) => {
      this.devices.update(list => [...list, newDevice]);
       alert('The device "' + newDevice.name + '" has been saved successfully!');
    },
    error: (err) => {
      alert('Error occurred while saving! Please try again.');
      console.error(err);
    }
    });
  }

  updateDevice(updatedDevice: Device){
    this.http.put<Device>(`${this.apiUrl}/${updatedDevice.serialNumber}`, updatedDevice).subscribe({
      next: (device) => {        this.devices.update(list => list.map(d => d.serialNumber === device.serialNumber ? device : d));
        alert('The device "' + device.name + '" has been updated successfully!');
      },
      error: (err) => {
        alert('Error occurred while updating! Please try again.');
        console.error(err);
      }
    });
  }

  deleteDeviceById(serial_number: string){
    this.http.delete(`${this.apiUrl}/${serial_number}`).subscribe({
      next: () => {
        this.devices.update(list => list.filter(d => d.serialNumber !== serial_number));
        alert('The device has been deleted successfully!');
      },
      error: (err) => {
        alert('Error occurred while deleting! Please try again.');
        console.error(err);
      }
    });
  }
}
