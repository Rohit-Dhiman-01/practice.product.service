package com.ecommerce.product.service.mappers;

import com.ecommerce.product.service.dtos.usersDtos.RegisterUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UpdateUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
