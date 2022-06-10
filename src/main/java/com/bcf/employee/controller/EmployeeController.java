package com.bcf.employee.controller;

import com.bcf.employee.dto.EmployeeDto;
import com.bcf.employee.dto.EmployeeEditDto;
import com.bcf.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeEditDto employeeEditDto) {
        log.debug("Received create employee request with body : {}", employeeEditDto);
        return ResponseEntity.ok(employeeService.registerEmployee(employeeEditDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable final UUID id,
                                                      @RequestBody EmployeeEditDto employeeEditDto) {
        log.debug("Received update employee request with body : {}", employeeEditDto);
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeEditDto));
    }

    @GetMapping
    public ResponseEntity<Page<?>> getEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(employeeService.getEmployees(pageNo, pageSize));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable final UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
