package com.bcf.employee.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistrationException extends RuntimeException{
    private static final long serialVersionUID = -7544397215449691686L;

    public UserRegistrationException(String message) {
        super(message);
        log.error(message, this);
    }
}
