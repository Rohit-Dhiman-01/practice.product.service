package com.ecommerce.product.service.services;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import com.ecommerce.product.service.mappers.ProductMapper;
import com.ecommerce.product.service.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PageAndSearchService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> search(Pageable pageable,
                                   String query){
        if (query == null) {
        return productRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(productMapper::toDto)
                .toList();
        }else {
            return productRepository.findByName(query, pageable)
                    .getContent()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }
    }
    public List<ProductDto> search( int pageNumber,
                                   int pageSize,
                                    Sort sortBy,
                                    String query){
        if (query == null) {
                return productRepository.findAll(PageRequest.of(pageNumber,pageSize,sortBy))
                        .getContent()
                        .stream()
                        .map(productMapper::toDto)
                    .toList();
        }else {
            return productRepository.findByName(query ,PageRequest.of(pageNumber,pageSize,sortBy))
                    .getContent()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }

    }
}