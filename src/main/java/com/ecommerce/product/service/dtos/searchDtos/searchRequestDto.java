package com.ecommerce.product.service.dtos.searchDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchRequestDto {
    private String query;
    private int pageNumber = 0 ;
    private int pageSize = 5;
    private String sortDirection = "ASC";
    private String sortBy = "id";
}
