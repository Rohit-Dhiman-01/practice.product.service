package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.searchDtos.searchRequestDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    public void search(@RequestBody searchRequestDto requestDto){

    }
}
