package com.ecommerce.product.service.elasticSearch;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIndexRepository extends ElasticsearchRepository<ProductIndex, Long> {
    List<ProductIndex> findByNameContainingOrDescriptionContainingAllIgnoreCase(
            String name, String description
    );
}
