package com.teamgrit.deepdive.skeleton.adapter.web;

import com.teamgrit.deepdive.skeleton.adapter.redis.RedisInventoryStore;
import com.teamgrit.deepdive.skeleton.application.CheckoutService;
import java.time.Duration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final RedisInventoryStore inventoryStore;

    public CheckoutController(CheckoutService checkoutService, RedisInventoryStore inventoryStore) {
        this.checkoutService = checkoutService;
        this.inventoryStore = inventoryStore;
    }

    @PostMapping("/inventory/{sku}/reset")
    public InventoryResponse reset(@PathVariable String sku, @RequestParam int quantity) {
        inventoryStore.reset(sku, quantity);
        return new InventoryResponse(sku, inventoryStore.remaining(sku));
    }

    @GetMapping("/inventory/{sku}")
    public InventoryResponse remaining(@PathVariable String sku) {
        return new InventoryResponse(sku, inventoryStore.remaining(sku));
    }

    @PostMapping("/checkout/{sku}/reserve")
    public CheckoutResponse reserve(
            @PathVariable String sku,
            @RequestParam(defaultValue = "500") long ttlMillis,
            @RequestParam(defaultValue = "0") long workDelayMillis
    ) {
        boolean reserved = checkoutService.reserveOne(
                sku,
                Duration.ofMillis(ttlMillis),
                Duration.ofMillis(workDelayMillis)
        );
        return new CheckoutResponse(sku, reserved, inventoryStore.remaining(sku));
    }

    public record InventoryResponse(String sku, int remaining) {
    }

    public record CheckoutResponse(String sku, boolean reserved, int remaining) {
    }
}
