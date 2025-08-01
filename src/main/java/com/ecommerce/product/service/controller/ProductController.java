package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import com.ecommerce.product.service.dtos.productDtos.RegisterProductRequest;
import com.ecommerce.product.service.entity.Category;
import com.ecommerce.product.service.entity.Product;
import com.ecommerce.product.service.repository.CategoryRepository;
import com.ecommerce.product.service.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getAllProduct(
            @RequestParam(required = false, name = "categoryId") Byte categoryId
    ){
        List<Product> productDtoList;
        if (categoryId == null){
            productDtoList =  productRepository.findWithCategoryId();
        } else {
            productDtoList = productRepository.findByCategoryId(categoryId);
        }
        return productDtoList.stream().map(this::toProductDto).toList();
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody RegisterProductRequest request,
            UriComponentsBuilder uriBuilder
    ){
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = this.toEntity(request,category);
        productRepository.save(product);

        var productDto = this.toProductDto(product);
        var uri = uriBuilder.path("products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody RegisterProductRequest request
    ){
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        this.update(request,category,product);
        productRepository.save(product);
        return ResponseEntity.ok(this.toProductDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ){
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

//    Helper functions
    private ProductDto toProductDto(Product product){
        return new ProductDto(product.getId(), product.getName(),product.getDescription(),product.getPrice(),product.getCategory().getId());
    }
    private Product toEntity(RegisterProductRequest request, Category category){
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        return product;
    }
    private void update(RegisterProductRequest request,Category category , Product product){
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(category);
    }
}
