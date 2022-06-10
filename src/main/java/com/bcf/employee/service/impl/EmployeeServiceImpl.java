package com.bcf.employee.service.impl;

import com.bcf.employee.domain.Employee;
import com.bcf.employee.dto.EmployeeDto;
import com.bcf.employee.dto.EmployeeEditDto;
import com.bcf.employee.exception.EmployeeException;
import com.bcf.employee.exception.RecordNotFoundException;
import com.bcf.employee.mapper.EmployeeMapper;
import com.bcf.employee.repository.EmployeeRepository;
import com.bcf.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    @Override
    public EmployeeDto registerEmployee(EmployeeEditDto employeeEditDto) {

        Employee employee = employeeMapper.toModel(employeeEditDto);

        if(nonNull(employeeEditDto.getSupervisorId())) {
            Employee supervisor =  employeeRepository.findById(employeeEditDto.getSupervisorId())
                    .orElseThrow(() -> new RecordNotFoundException(format("No supervisor found for id: %s",
                            employeeEditDto.getSupervisorId())));
            employee.setSupervisor(supervisor);
            supervisor.addSubordinate(employee);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto = employeeMapper.toDto(savedEmployee);
        employeeDto.setSubordinates(savedEmployee.getSubordinates().stream()
                .map(subordinate -> employeeMapper.toDto(subordinate)).collect(Collectors.toSet()));
        return employeeDto;
    }

    @Transactional
    @Override
    public EmployeeDto updateEmployee(UUID id, EmployeeEditDto employeeEditDto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(format("No employee found for id: %s", id)));

        if(id.equals(employeeEditDto.getSupervisorId()))
            throw new EmployeeException(format("Employee %s cannot become its own subordinate.", employeeEditDto.getName()));

        Employee employeeToUpdate = employeeMapper.toModel(employeeEditDto);
        if(nonNull(employeeEditDto.getSupervisorId())) {
            Employee supervisor =  employeeRepository.findById(employeeEditDto.getSupervisorId())
                    .orElseThrow(() -> new RecordNotFoundException(format("No supervisor found for id: %s",
                            employeeEditDto.getSupervisorId())));
            if(nonNull(employee.getSupervisor())
            && supervisor.getId().equals(employee.getSupervisor().getId())) {
                throw new EmployeeException(format("Employee %s cannot be assigned as a supervisor to %s" +
                        "as it is already a the supervisor of the same", supervisor.getName(), employee.getName()));
            }

            if(employee.getSubordinates().contains(supervisor)) // a Previously subordinate becoming a supervisor of it's own ex-supervisor
                employee.removeSubordinate(supervisor);

            employeeToUpdate.setSupervisor(supervisor);
        }

        employeeToUpdate.setId(employee.getId());
        employeeRepository.save(employeeToUpdate);

        return employeeMapper.toDto(employeeToUpdate);
    }

    @Override
    public Page<EmployeeDto> getEmployees(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return employeeRepository.findAll(paging)
                .map( employee -> {
                    EmployeeDto employeeDto =  employeeMapper.toDto(employee);
                    employeeDto.setSubordinates(employee.getSubordinates().stream()
                            .map(subordinate -> employeeMapper.toDto(subordinate)).collect(Collectors.toSet()));
                    return employeeDto;
                        }
                );
    }

    @Override
    public void deleteEmployee(UUID id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(format("No employee found for id: %s", id)));

        // Checking here instead of leaving it to the actual delete operation to throw an exception, since employee is already loaded
        if(!employee.getSubordinates().isEmpty())
            throw new EmployeeException(format("Employee %s cannot be deleted because it has subordinates.", employee.getName()));

        employeeRepository.delete(employee);
    }
}
