package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import com.ecommerce.product.service.dtos.searchDtos.searchRequestDto;
import com.ecommerce.product.service.services.PageAndSearchService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/page")
public class PageAndSearchController {
    private final PageAndSearchService searchService;

    @PostMapping
    public List<ProductDto> search(@RequestBody searchRequestDto requestDto){
        String sortBy;
        String sortDirection;
        Sort sort;
        if (requestDto.getSortBy() == null) {
            sortBy = "id";
        } else {
            sortBy = requestDto.getSortBy();
        }

        if (requestDto.getSortDirection() == null) {
            sortDirection = "ASC";
        } else {
            sortDirection = requestDto.getSortDirection();
        }

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        return searchService.search(PageRequest.of(requestDto.getPageNumber(), requestDto.getPageSize(),sort),
                requestDto.getQuery());
    }


    @GetMapping
    public List<ProductDto> search(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                   @RequestParam(required = false, defaultValue = "3") int pageSize,
                                   @RequestParam(required = false, defaultValue = "id") String sortBy, // name, id, price
                                   @RequestParam (required = false, defaultValue = "ASC") String sortDirection, // ASC, DESC
                                   @RequestParam (required = false)String query // name , price
    ){
        Sort sort ;
        if (sortDirection.equalsIgnoreCase("ASC")) {
            sort =Sort.by(sortBy).ascending();
        }else{
            sort =Sort.by(sortBy).descending();
        }
        return searchService.search(pageNumber, pageSize, sort, query);
    }
}
