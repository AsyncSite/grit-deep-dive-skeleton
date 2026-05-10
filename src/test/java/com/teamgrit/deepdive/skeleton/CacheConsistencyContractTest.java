package com.teamgrit.deepdive.skeleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamgrit.deepdive.skeleton.adapter.primary.InMemoryProductStore;
import com.teamgrit.deepdive.skeleton.application.ProductCatalogService;
import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class CacheConsistencyContractTest {

    @Autowired
    ProductCatalogService catalogService;

    @Autowired
    InMemoryProductStore productStore;

    @Autowired
    StringRedisTemplate redisTemplate;

    @BeforeEach
    void reset() {
        try {
            redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
        } catch (RuntimeException e) {
            Assumptions.abort("Redis is not running. Run `docker compose up -d redis` first.");
        }
        productStore.reset("sku-1", new BigDecimal("1000"));
    }

    @Test
    void cacheMissFillsRedisAndSecondReadReturnsSameVersion() {
        ProductSnapshot first = catalogService.getProduct("sku-1");
        ProductSnapshot second = catalogService.getProduct("sku-1");

        assertThat(first.version()).isEqualTo(1);
        assertThat(second).isEqualTo(first);
    }

    @Test
    void updateEvictsStaleCacheBeforeNextRead() {
        catalogService.getProduct("sku-1");

        catalogService.updatePrice("sku-1", new BigDecimal("1200"));
        ProductSnapshot afterUpdate = catalogService.getProduct("sku-1");

        assertThat(afterUpdate.version()).isEqualTo(2);
        assertThat(afterUpdate.price()).isEqualByComparingTo("1200");
    }
}
