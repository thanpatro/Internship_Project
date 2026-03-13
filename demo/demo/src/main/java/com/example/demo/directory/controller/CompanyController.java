package com.example.demo.directory.controller;

import com.example.demo.directory.dto.CompanyDto;
import com.example.demo.directory.entity.Company;
import com.example.demo.directory.mapper.CompanyMapper;
import com.example.demo.directory.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies(){
        Company company = new Company();
        return companyService.getAllCompanies().stream().map(companyMapper::convertToCompanyDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable String id){
        Company company = companyService.findById(id);
        CompanyDto companyDto = companyMapper.convertToCompanyDto(company);

        return ResponseEntity.ok(companyDto);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto){
        Company company = companyMapper.convertToCompany(companyDto);
        Company companySaved = companyService.saveCompany(company);
        CompanyDto companyDtoSaved = companyMapper.convertToCompanyDto(companySaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(companyDtoSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable String id, @RequestBody CompanyDto companyDto){
        Company company = companyMapper.convertToCompany(companyDto);
        Company companySaved = companyService.updateCompanyById(id, company);
        CompanyDto companyDtoSaved = companyMapper.convertToCompanyDto(companySaved);

        return ResponseEntity.ok(companyDtoSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String id){
        companyService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
