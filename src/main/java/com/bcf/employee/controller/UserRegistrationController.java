package com.bcf.employee.controller;

import com.bcf.employee.dto.UserDto;
import com.bcf.employee.dto.UserRegistrationDto;
import com.bcf.employee.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        log.debug("Registering user with details: {}", userRegistrationDto);
        return ResponseEntity.ok(userRegistrationService.registerUser(userRegistrationDto));
    }

}
