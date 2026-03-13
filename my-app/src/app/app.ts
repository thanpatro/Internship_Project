import { Component, inject, signal } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { CompanyService } from './services/company';
import { EmployeeService } from './services/employee';
import { DeviceService } from './services/device';
import { Sidebar } from './components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  imports: [Sidebar, RouterOutlet, RouterModule],
  standalone: true,
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'Inventory'

    public companyService = inject(CompanyService);
    public employeeService = inject(EmployeeService);
    public deviceService = inject(DeviceService);

    isSideMenuOpen = signal(false);
    
    toggleSideMenu() {
      this.isSideMenuOpen.update(open => !open);
    }

    selectCompany(id: string){
      this.companyService.selectCompany(id);
    }
}
