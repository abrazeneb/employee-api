package com.bcf.employee.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class EmployeeDto implements Serializable {
    private static final long serialVersionUID = 6653530378851882486L;
    private UUID id;
    private String name;
    private EmployeeDto supervisor;
    @Builder.Default
    private Set<EmployeeDto> subordinates = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeDto getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(EmployeeDto supervisor) {
        this.supervisor = supervisor;
    }

    public Set<EmployeeDto> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }
}
