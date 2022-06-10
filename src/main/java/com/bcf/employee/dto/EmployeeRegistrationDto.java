package com.bcf.employee.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRegistrationDto implements Serializable {
    private static final long serialVersionUID = -4602916688193898594L;

    private UUID supervisorId;
    @NonNull
    private String name;
}
