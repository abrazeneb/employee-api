package com.bcf.employee.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEditDto implements Serializable {
    private static final long serialVersionUID = -248041172468448114L;

    private UUID supervisorId;
    @NonNull
    private String name;
}
