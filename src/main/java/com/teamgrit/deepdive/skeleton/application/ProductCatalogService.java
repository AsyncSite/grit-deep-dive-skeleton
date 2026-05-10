package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.adapter.primary.InMemoryProductStore;
import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogService {

    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    private final InMemoryProductStore productStore;
    private final ProductCache productCache;

    public ProductCatalogService(InMemoryProductStore productStore, ProductCache productCache) {
        this.productStore = productStore;
        this.productCache = productCache;
    }

    public ProductSnapshot getProduct(String productId) {
        return productCache.read(productId)
                .orElseGet(() -> {
                    ProductSnapshot snapshot = productStore.find(productId);
                    productCache.write(snapshot, CACHE_TTL);
                    return snapshot;
                });
    }

    public ProductSnapshot updatePrice(String productId, BigDecimal price) {
        ProductSnapshot updated = productStore.updatePrice(productId, price);
        productCache.evict(productId);
        return updated;
    }
}
