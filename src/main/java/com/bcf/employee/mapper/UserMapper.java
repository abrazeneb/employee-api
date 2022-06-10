package com.bcf.employee.mapper;

import com.bcf.employee.domain.User;
import com.bcf.employee.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toModel(UserDto userDto);
}
