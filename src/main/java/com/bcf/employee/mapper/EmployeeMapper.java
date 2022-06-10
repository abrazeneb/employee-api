package com.bcf.employee.mapper;

import com.bcf.employee.domain.Employee;
import com.bcf.employee.dto.EmployeeDto;
import com.bcf.employee.dto.EmployeeEditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface EmployeeMapper {
    Employee toModel(EmployeeEditDto employeeRegistrationDto);

    @Mapping(target = "subordinates", ignore = true)
    EmployeeDto toDto(Employee employee);
}
