package com.example.demo.directory.dto;

public class EmployeeDto {

    private String id;
    private String name;
    private String email;
    private String Company_id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setCompanyId(String companyId) {
        this.Company_id = companyId;
    }

    public String getCompanyId() {
        return Company_id;
    }
}
