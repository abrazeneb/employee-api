package com.bcf.employee.service;

import com.bcf.employee.dto.EmployeeDto;
import com.bcf.employee.dto.EmployeeEditDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EmployeeService {
    EmployeeDto registerEmployee(EmployeeEditDto employeeEditDto);

    EmployeeDto updateEmployee(UUID id, EmployeeEditDto em);

    Page<EmployeeDto> getEmployees(Integer pageNo, Integer pageSize);

    void deleteEmployee(final UUID id);
}
