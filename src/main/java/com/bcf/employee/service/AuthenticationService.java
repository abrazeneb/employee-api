package com.bcf.employee.service;

import com.bcf.employee.dto.AuthenticationTokenResponseDto;
import com.bcf.employee.dto.UserDto;

public interface AuthenticationService {

    AuthenticationTokenResponseDto authenticate(final String username, final String password);

    UserDto getCurrentUser();
}
