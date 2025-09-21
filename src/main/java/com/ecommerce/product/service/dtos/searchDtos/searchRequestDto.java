package com.ecommerce.product.service.dtos.searchDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchRequestDto {
    private String query;
    private int pageNumber;
    private int pageSize;
}
