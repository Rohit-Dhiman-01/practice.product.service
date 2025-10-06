package com.ecommerce.product.service.elasticSearch;

import com.ecommerce.product.service.entity.Product;
import com.ecommerce.product.service.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductIndexService {
    private final ProductRepository productRepository;
    private final ProductIndexRepository productIndexRepository;

//  Page size for elastic
    private static final int PAGE_SIZE = 100;

//  Search using elastic
    public List<ProductIndex> searchProducts(String query) {
        return productIndexRepository.findByNameContainingOrDescriptionContainingAllIgnoreCase(
                query, query
        );
    }

//  If ES index is empty => do a full reindex. Otherwise, skip.
    @Transactional(readOnly = true)
    public void reindexIfEmpty() {
        long count = productIndexRepository.count();
        if (count > 0) {
            log.info("Elasticsearch already contains {} documents. Skipping reindex.", count);
            return;
        }
        log.info("Elasticsearch index empty. Starting reindex...");
        reindexAll();
    }


//   Force reindex: delete ES index contents and index everything from DB in pages.
    @Transactional(readOnly = true)
    public void reindexAll() {
        log.info("Starting full reindex of products into Elasticsearch...");
        productIndexRepository.deleteAll();

        long total = productRepository.count();
        if (total == 0) {
            log.info("No products found in database to index.");
            return;
        }

        int page = 0;
        Page<Product> productPage;
        do {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE);
            productPage = productRepository.findAll(pageable);

            List<ProductIndex> indexes = productPage.getContent().stream()
                    .map(this::toIndex)
                    .collect(Collectors.toList());

            if (!indexes.isEmpty()) {
                productIndexRepository.saveAll(indexes);
                log.info("Indexed {} products (page {}).", indexes.size(), page);
            }

            page++;
        } while (productPage.hasNext());

        log.info("Reindex complete. ES now has {} documents.", productIndexRepository.count());
    }

//  Helper Function
    private ProductIndex toIndex(Product p) {
        return ProductIndex.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice() != null ? p.getPrice().doubleValue() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
                .build();
    }
}