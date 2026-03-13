import { Component, computed, inject, Input } from '@angular/core';
import { CompanyService } from '../../services/company';
import { EmployeeService } from '../../services/employee';
import { DeviceService } from '../../services/device';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  @Input() isOpen = false;

  public companyService = inject(CompanyService);
  public employeeService = inject(EmployeeService);
  public deviceService = inject(DeviceService);

  employeesOfSelectedCompany = computed(() => {
    const companyId = this.companyService.selectedCompanyId();
    if(!companyId) return [];
    return this.employeeService.allEmployees().filter(e => e.companyId == companyId);
  })

  devicesOfSelectedEmployee = computed(() => {
    const employeeId = this.employeeService.selectedEmployeeId();
    if(!employeeId) return [];
    return this.deviceService.allDevices().filter(d => d.employeeId == employeeId);
  })

  selectCompany(id: string){
    console.log('Company selected:', id);
    this.companyService.selectedCompanyId.set(id);
    this.employeeService.selectedEmployeeId.set(undefined);
  }

  selectEmployee(id : string){
    console.log('Employee selected:', id);
    this.employeeService.selectedEmployeeId.set(id);
  }

  goBackToEmployees(){
     this.employeeService.selectedEmployeeId.set(undefined);
  }

  goBackToCompanies(){
    this.employeeService.selectedEmployeeId.set(undefined);
    this.companyService.selectedCompanyId.set(undefined);
  }
}
