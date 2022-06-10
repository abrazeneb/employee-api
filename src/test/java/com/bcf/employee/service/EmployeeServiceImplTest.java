package com.bcf.employee.service;

import com.bcf.employee.domain.Employee;
import com.bcf.employee.dto.EmployeeDto;
import com.bcf.employee.dto.EmployeeEditDto;
import com.bcf.employee.mapper.EmployeeMapper;
import com.bcf.employee.repository.EmployeeRepository;
import com.bcf.employee.service.impl.EmployeeServiceImpl;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl service;

    @Mock
    private EmployeeRepository repository;
    @Mock
    private EmployeeMapper employeeMapper;

    private EmployeeEditDto employeeEditDto ;
    private Employee employee ;
    private EmployeeDto employeeDto;
    private Employee savedEmployee ;

    @BeforeEach
    public void setup() {
        employeeEditDto = EmployeeEditDto.builder()
                .name("Test employee")
                .build();
        employee = new Employee();
        employee.setName("Test employee");

        employeeDto = new EmployeeDto();
        employeeDto.setName("Test employee");
        savedEmployee = employee;
        savedEmployee.setId(UUID.randomUUID());
    }

    @Test
    public void testRegisterEmployee() {

        // when
        when(employeeMapper.toModel(any())).thenReturn(employee);
        when(employeeMapper.toDto(any())).thenReturn(employeeDto);
        when(repository.save(any())).thenReturn(savedEmployee);

        service.registerEmployee(employeeEditDto);
        verify(repository, times(1)).save(employee);
    }

    @Test
    public void testUpdateEmployee() {
        //given
        employeeEditDto.setName("changed name");

        // when
        when(repository.findById(savedEmployee.getId())).thenReturn(Optional.of(savedEmployee));
        when(employeeMapper.toModel(any())).thenReturn(employee);
        when(employeeMapper.toDto(any())).thenReturn(employeeDto);
        when(repository.save(any())).thenReturn(savedEmployee);

        val updatedResult = service.updateEmployee(savedEmployee.getId(), employeeEditDto);

        verify(repository, times(1)).save(employee);
        assertNotNull(updatedResult);
    }
}
