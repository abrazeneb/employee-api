package com.bcf.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationDto implements Serializable {
    private static final long serialVersionUID = 893855013112164906L;

    private String username;
    private String password;
}
