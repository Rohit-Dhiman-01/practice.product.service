package com.ecommerce.product.service.mappers;

import com.ecommerce.product.service.dtos.orderDtos.OrderDto;
import com.ecommerce.product.service.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
