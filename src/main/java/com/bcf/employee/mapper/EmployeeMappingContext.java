package com.bcf.employee.mapper;

import com.bcf.employee.dto.EmployeeDto;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

public class EmployeeMappingContext {
    private Map<Object, Object> knownInstances = new IdentityHashMap<>();

    @BeforeMapping
    public <T extends EmployeeDto> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        return (T) knownInstances.get(source);
    }

    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget EmployeeDto target) {
        knownInstances.put( source, target);
    }
}
