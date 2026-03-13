import { Injectable, signal, inject } from '@angular/core';
import { Employee } from '../models/employee';
import { HttpClient } from '@angular/common/http';
import { Device } from '../models/device';
import { DeviceService } from './device';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private http = inject(HttpClient);
  private devices = inject(DeviceService);
  private apiUrl = 'http://localhost:8080/employees';

  private employees = signal<Employee[]>([]);

  selectedEmployeeId = signal<string | undefined>(undefined);

  constructor(){
    this.getEmployees();
  }

  getEmployees() {
    this.http.get<Employee[]>(this.apiUrl).subscribe(employees => {
      this.employees.set(employees);
    });
   }

   get allEmployees() {
    return this.employees;
   }

   getEmployeeById(id: string):Employee | undefined{
    return this.employees().find(e => e.id === id);
   }

   addEmployee(employee: Employee){
    this.http.post<Employee>(this.apiUrl, employee).subscribe({
    next: (newEmployee) => {
      this.employees.update(list => [...list, newEmployee]);
       alert('The employee "' + newEmployee.name + '" has been saved successfully!');
    },
    error: (err) => {
      alert('Error occurred while saving! Please try again.');
      console.error(err);
    }
    });
   }

   updateEmployee(updatedEmployee: Employee){
    this.http.put<Employee>(`${this.apiUrl}/${updatedEmployee.id}`, updatedEmployee).subscribe({
      next: (employee) => {
        this.employees.update(list => list.map(e => e.id === employee.id ? employee : e));
        alert('The employee "' + employee.name + '" has been updated successfully!');
      },
      error: (err) => {
        alert('Error occurred while updating! Please try again.');
        console.error(err);
      }
    }); 
   } 

   deleteEmployeeById(id:string){
    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: () => {
        this.employees.update(list => list.filter(e => e.id !== id));

        this.devices.allDevices.update((list: Device[]) => 
          list.filter((d: Device) => d.employeeId !== id)
        );
        alert('The employee has been deleted successfully!');
      } ,
      error: (err) => {
        alert('Error occurred while deleting! Please try again.');
        console.error(err);
      }
    });
   }

   selectEmployee(id: string) {
    this.selectedEmployeeId.set(id);
   }
}
