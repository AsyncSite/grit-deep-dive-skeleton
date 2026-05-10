package com.teamgrit.deepdive.skeleton.domain;

import java.math.BigDecimal;

public record ProductSnapshot(
        String productId,
        long version,
        BigDecimal price
) {
}
