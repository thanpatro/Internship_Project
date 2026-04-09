package com.example.demo.directory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "devices")
public class Device {

    @Id private String serialNumber;
    private String name;
    private String type;
    private String employeeId;
    private String companyId;

    public Device() {}

    public Device(String name, String type, String employeeId, String companyId){
        this.name = name;
        this.type = type;
        this.employeeId = employeeId;
        this.companyId = companyId;
    }

    public String getSerialNumber(){
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
}