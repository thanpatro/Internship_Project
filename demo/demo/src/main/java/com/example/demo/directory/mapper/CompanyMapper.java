package com.example.demo.directory.mapper;

import com.example.demo.directory.dto.CompanyDto;
import com.example.demo.directory.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyDto convertToCompanyDto(Company company) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        companyDto.setAddress(company.getAddress());

        return companyDto;
    }

    public Company convertToCompany(CompanyDto companyDto) {
        Company company = new Company();

        company.setName(companyDto.getName());
        company.setAddress(companyDto.getAddress());

        return company;
    }
}
