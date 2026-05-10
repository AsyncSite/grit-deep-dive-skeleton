package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.time.Duration;
import java.util.Optional;

public interface ProductCache {

    Optional<ProductSnapshot> read(String productId);

    void write(ProductSnapshot snapshot, Duration ttl);

    void evict(String productId);
}
