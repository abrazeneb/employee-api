package com.bcf.employee.service.impl;

import com.bcf.employee.domain.User;
import com.bcf.employee.dto.UserDto;
import com.bcf.employee.dto.UserRegistrationDto;
import com.bcf.employee.exception.UserRegistrationException;
import com.bcf.employee.mapper.UserMapper;
import com.bcf.employee.repository.UserRepository;
import com.bcf.employee.service.CustomUserDetailsService;
import com.bcf.employee.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        final String username = userRegistrationDto.getUsername();
       if(userRepository.findByUsername(username).isPresent())
           throw new UserRegistrationException(format("Unable to register user with username " +
                   "%s. Username not available",username));
       if(!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword()))
           throw new UserRegistrationException("Password does not match");
        User user = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                        .enabled(true)
                        .fullName(userRegistrationDto.getFullName())
                        .build();
       return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }
}
