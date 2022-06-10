package com.bcf.employee.service.impl;

import com.bcf.employee.domain.User;
import com.bcf.employee.dto.AuthenticationTokenResponseDto;
import com.bcf.employee.dto.UserDto;
import com.bcf.employee.exception.AuthenticationException;
import com.bcf.employee.mapper.UserMapper;
import com.bcf.employee.repository.UserRepository;
import com.bcf.employee.security.JwtUtils;
import com.bcf.employee.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public AuthenticationTokenResponseDto authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(user);
        return AuthenticationTokenResponseDto.builder().accessToken(jwt).build();
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new AuthenticationException("Not authenticated");
        }
        return UserMapper.INSTANCE.toDto((User) authentication.getPrincipal());
    }
}
