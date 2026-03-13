import { Component, inject, signal } from '@angular/core';
import { DeviceService } from '../../services/device';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Device } from '../../models/device';
import { EmployeeService } from '../../services/employee';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-devices',
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './devices.html',
  styleUrl: './devices.css',
})
export class DevicesComponent {
  private deviceService = inject(DeviceService);
  public employeeService = inject(EmployeeService);
  private form = inject(FormBuilder);

  devices = this.deviceService.allDevices;
  selectedDeviceId = signal<string | undefined>(undefined);
  foundDevice = signal<Device | undefined>(undefined);

  deviceForm = this.form.group({
    name : ['', [Validators.required]],
    type : ['', [Validators.required]],
    employeeId : [null, [Validators.required]]
  } )

  getDevice(serialNumber : string ){
    const device = this.deviceService.getDeviceById(serialNumber);
    this.selectedDeviceId.set(serialNumber);
    this.foundDevice.set(device);

    this.deviceForm.patchValue({
      name: device?.name,
      type: device?.type,
      employeeId: device?.employeeId as any
    });
  }

  createDevice(){
    const device = this.deviceForm.getRawValue() as any;
    
    this.deviceService.addDevice(device as Device);
    this.deviceForm.reset();
  }

  updateDevice(){
    const id = this.selectedDeviceId();
        const device = this.deviceForm.getRawValue() as any;
        
        if(device) {
            const deviceWithId: Device = { ...device, serialNumber: id } as Device;
            this.deviceService.updateDevice(deviceWithId);
            this.selectedDeviceId.set(undefined);
            this.deviceForm.reset();
          } 
  }

  deleteDevice(id: string){
    const hasConfirmed =window.confirm('Are you sure you want to delete this device?');
    if (hasConfirmed) {
      this.deviceService.deleteDeviceById(id);
    } 
  }
}
