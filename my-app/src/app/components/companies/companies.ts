import { Component, inject, signal } from '@angular/core';
import { CompanyService } from '../../services/company';
import { Company } from '../../models/company';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-companies',
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './companies.html',
  styleUrl: './companies.css',
}) 
export class CompaniesComponent {
  private companyService = inject(CompanyService);
  private form = inject(FormBuilder);

  companies = this.companyService.allCompanies;
  selectedCompanyId = signal<string | undefined>(undefined);
  foundCompany = signal<Company | undefined>(undefined);

  companyForm = this.form.group({
    name : ['', [Validators.required]],
    address : ['', [Validators.required]]
  } )

  getCompany(id: string) {
    const company = this.companyService.getCompanyById(id);
    this.selectedCompanyId.set(id);
    this.foundCompany.set(company);

    this.companyForm.patchValue({
      name: company?.name,
      address: company?.address 
    });
  }

  createCompany(){
    const company = this.companyForm.getRawValue() as Company;

    this.companyService.addCompany(company);
    this.companyForm.reset();
  }

  updateCompany(){
    const id = this.selectedCompanyId();
    const company = this.companyForm.getRawValue() as Company;

    if(company) {
      const companyWithId: Company = { ...company, id: id } as Company;
      this.companyService.updateCompany(companyWithId);
      this.selectedCompanyId.set(undefined);
      this.companyForm.reset();
    } 
  }

  deleteCompany(id : string) {
    const hasConfirmed =window.confirm('Are you sure you want to delete this company?');
    if (hasConfirmed) {
      this.companyService.deleteCompanyById(id);
    }
  }
}  
