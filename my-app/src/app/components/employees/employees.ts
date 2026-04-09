import { Component, inject, signal } from '@angular/core';
import { EmployeeService } from '../../services/employee';
import { Employee } from '../../models/employee';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CompanyService } from '../../services/company';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employees',
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './employees.html',
  styleUrl: './employees.css',
})
export class EmployeesComponent {
  private employeeService = inject(EmployeeService);
  public companyService = inject(CompanyService);
  private form = inject(FormBuilder);

  employees = this.employeeService.allEmployees;
  selectedEmployeeId = signal<string | undefined>(undefined);
  foundEmployee = signal<Employee | undefined>(undefined);

  employeeForm = this.form.group({
    name : ['', [Validators.required]],
    email : ['', [Validators.required, Validators.email]],
    companyId : ['', [Validators.required]]
  } )

  getEmployee(id : string){
     const employee = this.employeeService.getEmployeeById(id);
     this.selectedEmployeeId.set(id);
     this.foundEmployee.set(employee);

      this.employeeForm.patchValue({
        name: employee?.name,
        email: employee?.email,
        companyId: employee?.companyId as any
      });
  }

  createEmployee(){
    const employee = this.employeeForm.getRawValue() as any;

    this.employeeService.addEmployee(employee as Employee);
    this.employeeForm.reset();
  }

  updateEmployee(){
    const id = this.selectedEmployeeId();
    const employee = this.employeeForm.getRawValue() as any;
    
    if(employee) {
        const employeeWithId: Employee = { ...employee, id: id } as Employee;
        this.employeeService.updateEmployee(employeeWithId);
        this.selectedEmployeeId.set(undefined);
        this.employeeForm.reset();
      } 
  }

  deleteEmployee(id : string){
    const hasConfirmed =window.confirm('Are you sure you want to delete this employee?');
    if (hasConfirmed) {
      this.employeeService.deleteEmployeeById(id);
    }
  }
} 
