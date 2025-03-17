package org.example.testswoyo.server.mapper;

import org.example.testswoyo.server.dto.request.LoginUserRequest;
import org.example.testswoyo.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "name", source = "userRequest.username")
    User convertFromCreateLoginUserRequestToEntity(LoginUserRequest userRequest);
}
