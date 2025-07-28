package com.ecommerce.product.service.mappers;

import com.ecommerce.product.service.dtos.UserDto;
import com.ecommerce.product.service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
