package com.ecommerce.product.service.mappers;

import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
