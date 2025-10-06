package com.ecommerce.product.service.elasticSearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductIndexInitializer {
    private final ProductIndexService productIndexService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            productIndexService.reindexIfEmpty();
        } catch (Exception ex) {
            log.error("Failed to initialize Elasticsearch index on startup", ex);
        }
    }
}
