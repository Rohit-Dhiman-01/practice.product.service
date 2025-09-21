package com.ecommerce.product.service.services;

import com.ecommerce.product.service.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class searchService {
    private final ProductRepository productRepository;
    public List<?> search(String query, int pageNumber, int pageSize){
        return null;
    }
}