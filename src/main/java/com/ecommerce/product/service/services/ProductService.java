package com.ecommerce.product.service.services;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import com.ecommerce.product.service.dtos.productDtos.RegisterProductRequest;
import com.ecommerce.product.service.entity.Category;
import com.ecommerce.product.service.entity.Product;
import com.ecommerce.product.service.mappers.ProductMapper;
import com.ecommerce.product.service.repository.CategoryRepository;
import com.ecommerce.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Byte categoryId) {
        List<Product> products = (categoryId == null)
                ? productRepository.findWithCategoryId()
                : productRepository.findByCategoryId(categoryId);

        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto createProduct(RegisterProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(Long id, RegisterProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        update(request, category, product);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }

//    Helper functions
    private void update(RegisterProductRequest request, Category category, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(category);
    }

}
