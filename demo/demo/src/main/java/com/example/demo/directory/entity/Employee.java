package com.example.demo.directory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class Employee {

    @Id private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String companyId;

    public Employee() {}

    public Employee(String name, String email, String companyId) {
        this.name = name;
        this.email = email;
        this.companyId = companyId;
    }

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
}
