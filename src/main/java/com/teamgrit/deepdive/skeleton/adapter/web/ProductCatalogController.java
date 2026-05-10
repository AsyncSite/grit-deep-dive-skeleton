package com.teamgrit.deepdive.skeleton.adapter.web;

import com.teamgrit.deepdive.skeleton.adapter.primary.InMemoryProductStore;
import com.teamgrit.deepdive.skeleton.application.ProductCatalogService;
import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCatalogController {

    private final InMemoryProductStore productStore;
    private final ProductCatalogService catalogService;

    public ProductCatalogController(InMemoryProductStore productStore, ProductCatalogService catalogService) {
        this.productStore = productStore;
        this.catalogService = catalogService;
    }

    @PostMapping("/products/{productId}/reset")
    public ProductSnapshot reset(@PathVariable String productId, @RequestParam BigDecimal price) {
        productStore.reset(productId, price);
        return catalogService.getProduct(productId);
    }

    @GetMapping("/products/{productId}")
    public ProductSnapshot getProduct(@PathVariable String productId) {
        return catalogService.getProduct(productId);
    }

    @PostMapping("/products/{productId}/price")
    public ProductSnapshot updatePrice(@PathVariable String productId, @RequestParam BigDecimal price) {
        return catalogService.updatePrice(productId, price);
    }
}
