package com.teamgrit.deepdive.skeleton.adapter.primary;

import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryProductStore {

    private final Map<String, ProductSnapshot> products = new ConcurrentHashMap<>();

    public void reset(String productId, BigDecimal price) {
        products.put(productId, new ProductSnapshot(productId, 1, price));
    }

    public ProductSnapshot find(String productId) {
        ProductSnapshot snapshot = products.get(productId);
        if (snapshot == null) {
            throw new IllegalArgumentException("unknown product: " + productId);
        }
        return snapshot;
    }

    public ProductSnapshot updatePrice(String productId, BigDecimal price) {
        return products.compute(productId, (key, current) -> {
            if (current == null) {
                return new ProductSnapshot(productId, 1, price);
            }
            return new ProductSnapshot(productId, current.version() + 1, price);
        });
    }
}
