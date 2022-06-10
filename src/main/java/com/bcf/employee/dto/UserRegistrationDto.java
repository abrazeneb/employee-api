package com.bcf.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDto implements Serializable {
    private static final long serialVersionUID = 4938117450283668449L;

    @NotEmpty(message = "Full name is required")
    public String fullName;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password required")
    private String password;
    @NotEmpty(message = "You need to confirm the password")
    private String confirmPassword;
}
