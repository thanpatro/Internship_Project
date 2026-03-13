import { Injectable, signal, inject} from '@angular/core';
import { Company } from '../models/company';
import { HttpClient } from '@angular/common/http';
import { DeviceService } from './device';
import { EmployeeService } from './employee';
import { Employee } from '../models/employee';
import { Device } from '../models/device';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
   private http = inject(HttpClient);
   private employees = inject(EmployeeService);
   private devices = inject(DeviceService);
   private apiUrl = 'http://localhost:8080/companies';

   private companies = signal<Company[]>([]);

   selectedCompanyId = signal<string | undefined>(undefined);

   constructor() {
      this.getCompanies();
   }

   getCompanies() {
    this.http.get<Company[]>(this.apiUrl).subscribe(companies => {
      this.companies.set(companies);
    });
   }

   get allCompanies() {
    return this.companies;
   }

   getCompanyById(id: string):Company | undefined{
    return this.companies().find(c => c.id === id);
   }

   addCompany(company: Company){
    this.http.post<Company>(this.apiUrl, company).subscribe({
    next: (newCompany) => {
      this.companies.update(list => [...list, newCompany]);
       alert('The company "' + newCompany.name + '" has been saved successfully!');
    },
    error: (err) => {
      alert('Error occurred while saving! Please try again.');
      console.error(err);
    }
    });
   }

   updateCompany(updatedCompany: Company){
    this.http.put<Company>(`${this.apiUrl}/${updatedCompany.id}`, updatedCompany).subscribe({
      next: (company) => {        this.companies.update(list => list.map(c => c.id === company.id ? company : c));
        alert('The company "' + company.name + '" has been updated successfully!');
      },
      error: (err) => {
        alert('Error occurred while updating! Please try again.');
        console.error(err);
      }
    });
   }

   deleteCompanyById(id: string) {
  this.http.delete(`${this.apiUrl}/${id}`).subscribe({
    next: () => {
      this.companies.update(list => [...list.filter(c => String(c.id) !== String(id))]);

      this.employees.allEmployees.update(list => 
        [...list.filter(e => String(e.companyId) !== String(id))]
      );
      this.devices.allDevices.update(list => 
        [...list.filter(d => String(d.companyId) !== String(id))]
      );

      this.devices.getDevices();

      alert('The company has been deleted successfully!');
    },
    error: (err) => {
      alert('Error occurred while deleting! Please try again.');
      console.error(err);
    }
  });
}
       
  selectCompany(id: string) {
    this.selectedCompanyId.set(id);
  }
}

