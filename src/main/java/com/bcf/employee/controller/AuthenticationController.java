package com.bcf.employee.controller;

import com.bcf.employee.dto.AuthenticationDto;
import com.bcf.employee.dto.AuthenticationTokenResponseDto;
import com.bcf.employee.dto.UserDto;
import com.bcf.employee.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationTokenResponseDto> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        log.debug("Received login request for username: {}", authenticationDto.getUsername());
        return ResponseEntity.ok(authenticationService.authenticate(authenticationDto.getUsername(), authenticationDto.getPassword()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }
}
