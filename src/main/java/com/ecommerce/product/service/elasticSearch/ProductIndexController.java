package com.ecommerce.product.service.elasticSearch;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/index")
@RequiredArgsConstructor
public class ProductIndexController {
    private final ProductIndexService productIndexService;

//   Trigger reindexing.
    @PostMapping("/reindex")
    public ResponseEntity<String> reindex(@RequestParam(name = "force", defaultValue = "false") boolean force) {
        if (force) {
            productIndexService.reindexAll();
            return ResponseEntity.ok("Reindex all products into Elasticsearch (force=true).");
        } else {
            productIndexService.reindexIfEmpty();
            return ResponseEntity.ok("Reindex triggered (only if index was empty).");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductIndex>> searchProducts(@RequestParam("query") String query) {
        List<ProductIndex> results = productIndexService.searchProducts(query);
        return ResponseEntity.ok(results);
    }
}
