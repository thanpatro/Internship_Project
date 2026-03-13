import { Routes } from '@angular/router';
import { CompaniesComponent } from './components/companies/companies';
import { EmployeesComponent } from './components/employees/employees';
import { DevicesComponent } from './components/devices/devices';

export const routes: Routes = [
   {path: 'companies', component: CompaniesComponent},
   {path: 'employees', component: EmployeesComponent},
   {path: 'devices', component: DevicesComponent},

   { path: '', redirectTo: 'companies', pathMatch: 'full' }
];
