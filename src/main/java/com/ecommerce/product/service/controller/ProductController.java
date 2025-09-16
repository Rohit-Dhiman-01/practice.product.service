package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import com.ecommerce.product.service.dtos.productDtos.RegisterProductRequest;
import com.ecommerce.product.service.entity.Category;
import com.ecommerce.product.service.entity.Product;
import com.ecommerce.product.service.mappers.ProductMapper;
import com.ecommerce.product.service.repository.CategoryRepository;
import com.ecommerce.product.service.repository.ProductRepository;
import java.util.List;

import com.ecommerce.product.service.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Products API")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(required = false, name = "categoryId") Byte categoryId
    ){
        return productService.getAllProducts(categoryId);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody RegisterProductRequest request,
            UriComponentsBuilder uriBuilder
    ){
        ProductDto productDto = productService.createProduct(request);
        var uri = uriBuilder.path("products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody RegisterProductRequest request
    ){
        ProductDto productDto = productService.updateProduct(id, request);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
