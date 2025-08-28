package com.ecommerce.product.service.mappers;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import com.ecommerce.product.service.dtos.productDtos.RegisterProductRequest;
import com.ecommerce.product.service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);

    Product toEntity(RegisterProductRequest productDto);

//    @Mapping(target = "id", ignore = true)
//    void update(ProductDto productDto, @MappingTarget Product product);
}
