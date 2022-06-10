package com.bcf.employee.service;

import com.bcf.employee.dto.UserDto;
import com.bcf.employee.dto.UserRegistrationDto;

public interface UserRegistrationService {
    UserDto registerUser(UserRegistrationDto userRegistrationDto);
}
